package litianly.myMiniResume;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import litianly.myMiniResume.model.BasicInfo;
import litianly.myMiniResume.model.Education;
import litianly.myMiniResume.model.Experience;
import litianly.myMiniResume.model.Project;
import litianly.myMiniResume.util.DateUtil;
import litianly.myMiniResume.util.ModelUtils;

public class MainActivity extends AppCompatActivity {

    private static final String MODEL_EDUCATIONS = "educations";
    private static final String MODEL_EXPERIENCES = "experiences";
    private static final String MODEL_PROJECTS = "projects";
    private static final String MODEL_BASIC_INFO = "basic_info";

    private static final int REQ_CODE_EDUCATION_EDIT = 100;
    private static final int REQ_CODE_EXPERIENCE_EDIT = 101;
    private static final int REQ_CODE_PROJECT_EDIT = 102;
    public static final int REQ_CODE_BASIC_INFO_EDIT = 103;

    private BasicInfo basicInfo = new BasicInfo();
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Project> projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
        setupUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            switch(requestCode) {
                case REQ_CODE_BASIC_INFO_EDIT:
                    BasicInfo basicInfo = data.getParcelableExtra(BasicInfoEditActivity.KEY_BASIC_INFO);
                    updateBasicInfo(basicInfo);
                    break;
                case REQ_CODE_EDUCATION_EDIT:
                    String educationID = data.getStringExtra(EducationEditActivity.KEY_EDUCATION_ID);
                    if (educationID != null) {
                        deleteEducation(educationID);
                    } else {
                        Education education = data.getParcelableExtra(EducationEditActivity.KEY_EDUCATION);
                        updateEducation(education);
                    }
                    break;
                case REQ_CODE_EXPERIENCE_EDIT:
                    String experienceID = data.getStringExtra(ExperienceEditActivity.KEY_EXPERIENCE_ID);
                    if (experienceID != null) {
                        deleteExperience(experienceID);
                    } else {
                        Experience experience = data.getParcelableExtra(ExperienceEditActivity.KEY_EXPERIENCE);
                        updateExperience(experience);
                    }
                    break;
                case REQ_CODE_PROJECT_EDIT:
                    String projectID = data.getStringExtra(ProjectEditActivity.KEY_PROJECT_ID);
                    if (projectID != null) {
                        deleteProject(projectID);
                    } else {
                        Project project = data.getParcelableExtra(ProjectEditActivity.KEY_PROJECT);
                        updateProject(project);
                    }
                    break;
            }
        }
    }

    private void loadData(){
        BasicInfo savedBasicInfo = ModelUtils.read(this, MODEL_BASIC_INFO, new TypeToken<BasicInfo>(){});
        basicInfo = savedBasicInfo == null ? new BasicInfo(): savedBasicInfo;

        List<Education> savedEducation = ModelUtils.read(this, MODEL_EDUCATIONS, new TypeToken<List<Education>>(){});
        educations = savedEducation == null ? new ArrayList<Education>() : savedEducation;

        List<Experience> savedExperience = ModelUtils.read(this, MODEL_EXPERIENCES, new TypeToken<List<Experience>>(){});
        experiences = savedExperience == null ? new ArrayList<Experience>() : savedExperience;

        List<Project> savedProject = ModelUtils.read(this, MODEL_PROJECTS, new TypeToken<List<Project>>(){});
        projects = savedProject == null ? new ArrayList<Project>() : savedProject;
    }

//    private void fakeData(){
//        basicInfo.name = "Gallen Yao";
//        basicInfo.email = "yltlgallen17@gmail.com";
//
//        educations = new ArrayList<>();
//        Education education1 = new Education();
//        Education education2 = new Education();
//
//        education1.school = "CMU";
//
//        education1.startDate = DateUtil.stringToDate("09/2015");
//        education1.endDate = DateUtil.stringToDate("08/2016");
//
//        education1.courses = new ArrayList<>();
//        education1.courses.add("Mobile development");
//        education1.courses.add("Data structure for application programmer");
//        education1.courses.add("Architecture for software systems");
//
//
//        education2.school = "NKU";
//
//        education2.startDate = DateUtil.stringToDate("09/2011");
//        education2.endDate = DateUtil.stringToDate("06/2015");
//
//        education2.courses = new ArrayList<>();
//        education2.courses.add("JAVA");
//        education2.courses.add("Database");
//        education2.courses.add("Architecture");
//
//        educations.add(education1);
//        educations.add(education2);
//    }

    private void setupUI() {
        setContentView(R.layout.activity_main);

        ImageButton addEducationBtn = (ImageButton) findViewById(R.id.add_education_btn);
        addEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });

        ImageButton addExperienceBtn = (ImageButton) findViewById(R.id.add_experience_btn);
        addExperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EXPERIENCE_EDIT);
            }
        });

        ImageButton addProjectBtn = (ImageButton) findViewById(R.id.add_project_btn);
        addProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                startActivityForResult(intent, REQ_CODE_PROJECT_EDIT);
            }
        });

        setupBasicInfo();
        setupEducations();
        setupExperiences();
        setupProjects();

    }

    private void setupBasicInfo() {
        ((TextView) findViewById(R.id.name)).setText(TextUtils.isEmpty(basicInfo.name)
                ? "Your name"
                : basicInfo.name);
        ((TextView) findViewById(R.id.email)).setText(TextUtils.isEmpty(basicInfo.email)
                ? "Your email"
                : basicInfo.email);

        ImageView userPicture = (ImageView) findViewById(R.id.user_pic);
        if (basicInfo.imagePath != null) {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(basicInfo.imagePath, options);

            userPicture.setImageBitmap(bitmap);
        } else {
            userPicture.setImageResource(R.drawable.user_ghost);
        }

        findViewById(R.id.edit_basic_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BasicInfoEditActivity.class);
                intent.putExtra(BasicInfoEditActivity.KEY_BASIC_INFO, basicInfo);
                startActivityForResult(intent, REQ_CODE_BASIC_INFO_EDIT);
            }
        });
    }


    private void updateBasicInfo(BasicInfo basicInfo) {
        ModelUtils.save(this, MODEL_BASIC_INFO, basicInfo);

        this.basicInfo = basicInfo;
        setupBasicInfo();
    }


    private void setupEducations() {
        LinearLayout educationsLayout = (LinearLayout) findViewById(R.id.educations);
        educationsLayout.removeAllViews();
        for (Education education: educations) {
            View educationView = getLayoutInflater().inflate(R.layout.education_item, null);
            getEducationView(educationView, education);
            educationsLayout.addView(educationView);
        }
    }

    private void getEducationView(View educationView, final Education education) {
        String schoolInfo = education.school + " " + education.major + " (" + DateUtil.dateToString(education.startDate) + " ~ " + DateUtil.dateToString(education.endDate) + ")";
        ((TextView) educationView.findViewById(R.id.education_school)).setText(schoolInfo);
        ((TextView) educationView.findViewById(R.id.education_courses)).setText(formatItems(education.courses));
        educationView.findViewById(R.id.edit_education_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                intent.putExtra(EducationEditActivity.KEY_EDUCATION, education);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });

    }


    private void updateEducation(Education education) {
        boolean found = false;
        for (int i = 0; i < educations.size(); i++) {
            Education e = educations.get(i);
            if (TextUtils.equals(e.id, education.id)) {
                found = true;
                educations.set(i, education);
                break;
            }
        }

        if(!found) {
            educations.add(education);
        }

        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducations();
    }

    private void deleteEducation(@NonNull String educationID) {
        for (int i = 0; i < educations.size(); i++) {
            Education e = educations.get(i);
            if (TextUtils.equals(e.id, educationID)) {
                educations.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducations();
    }


    private void setupExperiences() {
        LinearLayout experiencesLayout = (LinearLayout) findViewById(R.id.experiences);
        experiencesLayout.removeAllViews();
        for (Experience experience: experiences) {
            View experienceView = getLayoutInflater().inflate(R.layout.experience_item, null);
            getExperienceView(experienceView, experience);
            experiencesLayout.addView(experienceView);
        }
    }

    private void getExperienceView(View experienceView, final Experience experience) {
        String experienceInfo = experience.company + " " + experience.position + " (" + DateUtil.dateToString(experience.startDate) + " ~ " + DateUtil.dateToString(experience.endDate) + ")";
        ((TextView) experienceView.findViewById(R.id.experience_company)).setText(experienceInfo);
        ((TextView) experienceView.findViewById(R.id.experience_details)).setText(formatItems(experience.jobs));
        experienceView.findViewById(R.id.edit_experience_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                intent.putExtra(ExperienceEditActivity.KEY_EXPERIENCE, experience);
                startActivityForResult(intent, REQ_CODE_EXPERIENCE_EDIT);
            }
        });
    }

    private void updateExperience(Experience experience) {
        boolean found = false;
        for (int i = 0; i < experiences.size(); i++) {
            Experience e = experiences.get(i);
            if (TextUtils.equals(e.id, experience.id)) {
                found = true;
                experiences.set(i, experience);
                break;
            }
        }

        if(!found) {
            experiences.add(experience);
        }

        ModelUtils.save(this, MODEL_EXPERIENCES, experiences);
        setupExperiences();
    }

    private void deleteExperience(@NonNull String experienceID) {
        for (int i = 0; i < experiences.size(); i++) {
            Experience e = experiences.get(i);
            if (TextUtils.equals(e.id, experienceID)) {
                experiences.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_EXPERIENCES, experiences);
        setupExperiences();
    }


    private void setupProjects() {
        LinearLayout projectsLayout = (LinearLayout) findViewById(R.id.projects);
        projectsLayout.removeAllViews();
        for (Project project: projects) {
            View projectView = getLayoutInflater().inflate(R.layout.project_item, null);
            getProjectView(projectView, project);
            projectsLayout.addView(projectView);
        }
    }

    private void getProjectView(View projectView, final Project project) {
        String projectInfo = project.topic + " " + " (" + DateUtil.dateToString(project.startDate) + " ~ " + DateUtil.dateToString(project.endDate) + ")";
        ((TextView) projectView.findViewById(R.id.project_topic)).setText(projectInfo);
        ((TextView) projectView.findViewById(R.id.project_details)).setText(formatItems(project.details));
        projectView.findViewById(R.id.edit_project_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                intent.putExtra(ProjectEditActivity.KEY_PROJECT, project);
                startActivityForResult(intent, REQ_CODE_PROJECT_EDIT);
            }
        });
    }

    private void updateProject(Project project) {
        boolean found = false;
        for (int i = 0; i < projects.size(); i++) {
            Project p = projects.get(i);
            if (TextUtils.equals(p.id, project.id)) {
                found = true;
                projects.set(i, project);
                break;
            }
        }

        if(!found) {
            projects.add(project);
        }

        ModelUtils.save(this, MODEL_PROJECTS, projects);
        setupProjects();
    }

    private void deleteProject(@NonNull String projectID) {
        for (int i = 0; i < projects.size(); i++) {
            Project p = projects.get(i);
            if (TextUtils.equals(p.id, projectID)) {
                projects.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_PROJECTS, projects);
        setupProjects();
    }



    public static String formatItems(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (String item: items) {
            sb.append(' ').append('-').append(' ').append(item).append('\n');
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
