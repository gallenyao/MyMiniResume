package litianly.myMiniResume.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import litianly.myMiniResume.util.DateUtil;

public class Experience implements Parcelable {
    public String id;
    public String company;
    public String position;
    public Date startDate;
    public Date endDate;
    public List<String> jobs;

    public Experience() {
        id = UUID.randomUUID().toString();
    }

    protected Experience(Parcel in) {

        id = in.readString();
        company = in.readString();
        position = in.readString();
        startDate = DateUtil.stringToDate(in.readString());
        endDate = DateUtil.stringToDate(in.readString());
        jobs = in.createStringArrayList();
    }


    public static final Creator<Experience> CREATOR = new Creator<Experience>() {
        @Override
        public Experience createFromParcel(Parcel in) {
            return new Experience(in);
        }

        @Override
        public Experience[] newArray(int size) {
            return new Experience[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(company);
        parcel.writeString(position);
        parcel.writeString(DateUtil.dateToString(startDate));
        parcel.writeString(DateUtil.dateToString(endDate));
        parcel.writeStringList(jobs);
    }
}
