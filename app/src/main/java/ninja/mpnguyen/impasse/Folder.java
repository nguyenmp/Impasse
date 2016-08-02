package ninja.mpnguyen.impasse;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

class Folder implements Serializable, Parcelable {
    public final String name;
    public final Folder[] folders;
    public final Value[] values;

    public Folder(String name, Folder[] folders, Value[] values) {
        this.name = name;
        this.folders = folders;
        this.values = values;
    }

    public Folder(Parcel in) {
        name = in.readInt() == 0 ? null : in.readString();
        folders = in.createTypedArray(Folder.CREATOR);
        values = in.createTypedArray(Value.CREATOR);
    }

    public static final Creator<Folder> CREATOR = new Creator<Folder>() {
        @Override
        public Folder createFromParcel(Parcel in) {
            return new Folder(in);
        }

        @Override
        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(name == null ? 0 : 1);
        if (name != null) out.writeString(name);

        out.writeTypedArray(folders, flags);
        out.writeTypedArray(values, flags);
    }
}
