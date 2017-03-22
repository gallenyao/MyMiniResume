package litianly.myMiniResume.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import litianly.myMiniResume.util.DateUtil;

public class Project implements Parcelable {

    public String id;
    public String topic;
    public Date startDate;
    public Date endDate;
    public List<String> details;

    public Project() {
        id = UUID.randomUUID().toString();
    }

    protected Project(Parcel in) {
        id = in.readString();
        topic = in.readString();
        startDate = DateUtil.stringToDate(in.readString());
        endDate = DateUtil.stringToDate(in.readString());
        details = in.createStringArrayList();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(topic);
        parcel.writeString(DateUtil.dateToString(startDate));
        parcel.writeString(DateUtil.dateToString(endDate));
        parcel.writeStringList(details);
    }
}
