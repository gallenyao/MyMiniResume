package litianly.myMiniResume;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;

import litianly.myMiniResume.model.Experience;
import litianly.myMiniResume.util.DateUtil;


public class ExperienceEditActivity extends EditBaseActivity<Experience> {
    public static final String KEY_EXPERIENCE = "experience";
    public static final String KEY_EXPERIENCE_ID = "experience_id";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_experience_edit;
    }

    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.experience_edit_delete).setVisibility(View.GONE);
    }

    @Override
    protected void setupUIForEdit(@NonNull final Experience data) {
        ((EditText) findViewById(R.id.experience_edit_company)).setText(data.company);
        ((EditText) findViewById(R.id.experience_edit_position)).setText(data.position);
        ((EditText) findViewById(R.id.experience_edit_start_date)).setText(DateUtil.dateToString(data.startDate));
        ((EditText) findViewById(R.id.experience_edit_end_date)).setText(DateUtil.dateToString(data.endDate));
        ((EditText) findViewById(R.id.experience_edit_jobs)).setText(TextUtils.join("\n", data.jobs));
        findViewById(R.id.experience_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_EXPERIENCE_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void saveAndExit(@Nullable Experience data) {
        if (data == null) {
            data = new Experience();
        }
        data.company = ((EditText) findViewById(R.id.experience_edit_company)).getText().toString();
        data.position = ((EditText) findViewById(R.id.experience_edit_position)).getText().toString();
        data.startDate = DateUtil.stringToDate(((EditText) findViewById(R.id.experience_edit_start_date)).getText().toString());
        data.endDate = DateUtil.stringToDate(((EditText) findViewById(R.id.experience_edit_end_date)).getText().toString());
        data.jobs = Arrays.asList(TextUtils.split(((EditText) findViewById(R.id.experience_edit_jobs)).getText().toString(), "\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXPERIENCE, data);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected Experience initializeData() {
        return getIntent().getParcelableExtra(KEY_EXPERIENCE);
    }
}
