package com.alireza.todolists.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_task")
public class Task implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String tittle;
    private int importance = IMPORTANCE_NORMAL;
    private boolean completed;

    public static final int IMPORTANCE_HIGH=2;
    public static final int IMPORTANCE_NORMAL=1;
    public static final int IMPORTANCE_LOW=0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.tittle);
        dest.writeInt(this.importance);
        dest.writeByte(this.completed ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.tittle = source.readString();
        this.importance = source.readInt();
        this.completed = source.readByte() != 0;
    }

    public Task() {
    }

    protected Task(Parcel in) {
        this.id = in.readLong();
        this.tittle = in.readString();
        this.importance = in.readInt();
        this.completed = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Parcelable.Creator<Task>(){
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
