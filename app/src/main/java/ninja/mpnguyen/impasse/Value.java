package ninja.mpnguyen.impasse;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

class Value implements Serializable, Parcelable {
    public final String name;
    public final String data;

    Value(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public Value(Parcel in) {
        name = in.readString();
        data = in.readString();
    }

    public static final Creator<Value> CREATOR = new Creator<Value>() {
        @Override
        public Value createFromParcel(Parcel in) {
            return new Value(in);
        }

        @Override
        public Value[] newArray(int size) {
            return new Value[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(data);
    }
}
