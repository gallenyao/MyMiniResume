package litianly.myMiniResume;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;

import litianly.myMiniResume.model.Education;
import litianly.myMiniResume.util.DateUtil;


public class EducationEditActivity extends EditBaseActivity<Education> {

    public static final String KEY_EDUCATION = "education";
    public static final String KEY_EDUCATION_ID = "education_id";



    @Override
    protected int getLayoutId() {
        return R.layout.activity_education_edit;
    }

    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.education_edit_delete).setVisibility(View.GONE);
    }

    @Override
    protected void setupUIForEdit(@NonNull final Education data) {
        ((EditText) findViewById(R.id.education_edit_school)).setText(data.school);
        ((EditText) findViewById(R.id.education_edit_major)).setText(data.major);
        ((EditText) findViewById(R.id.education_edit_start_date)).setText(DateUtil.dateToString(data.startDate));
        ((EditText) findViewById(R.id.education_edit_end_date)).setText(DateUtil.dateToString(data.endDate));
        ((EditText) findViewById(R.id.education_edit_courses)).setText(TextUtils.join("\n", data.courses));

        findViewById(R.id.education_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_EDUCATION_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

    }

    @Override
    protected void saveAndExit(@Nullable Education data) {
        if (data == null) {
            data = new Education();
        }
        data.school = ((EditText) findViewById(R.id.education_edit_school)).getText().toString();
        data.major = ((EditText) findViewById(R.id.education_edit_major)).getText().toString();
        data.startDate = DateUtil.stringToDate(((EditText) findViewById(R.id.education_edit_start_date)).getText().toString());
        data.endDate = DateUtil.stringToDate(((EditText) findViewById(R.id.education_edit_end_date)).getText().toString());
        data.courses = Arrays.asList(TextUtils.split(((EditText) findViewById(R.id.education_edit_courses)).getText().toString(), "\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EDUCATION, data);
        setResult(Activity.RESULT_OK, resultIntent);
        Log.e("save edit","0000000000000");
        finish();
    }

    @Override
    protected Education initializeData() {
        return getIntent().getParcelableExtra(KEY_EDUCATION);
    }


}
