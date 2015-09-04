package henriiv.queueapp20;

import android.os.Parcel;
import android.os.Parcelable;

public class PassEvent implements Parcelable {
    private String name;
    private String info;
    private String begin;
    private String end;

    public PassEvent(String name, String info, String begin, String end){
        this.name = name;
        this.info = info;
        this.begin = begin;
        this.end = end;
    }


    public PassEvent(Parcel in){
        String[] data = new String[4];

        in.readStringArray(data);
        this.name = data[0];
        this.info = data[1];
        this.begin = data[2];
        this.end = data[3];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.name,
                this.info,
                this.begin,
                this.end});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PassEvent createFromParcel(Parcel in) {
            return new PassEvent(in);
        }

        public PassEvent[] newArray(int size) {
            return new PassEvent[size];
        }
    };
}
