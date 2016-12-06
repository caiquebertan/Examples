package br.com.examples.caique.examplesproject.common.controller.exceptions;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by FBisca on 16/06/2015.
 */
public class ServiceException extends TaskException implements Parcelable {

    public static final Creator<ServiceException> CREATOR = new Creator<ServiceException>() {
        @Override
        public ServiceException createFromParcel(Parcel in) {
            return new ServiceException(in);
        }

        @Override
        public ServiceException[] newArray(int size) {
            return new ServiceException[size];
        }
    };

    private int code;
    private String message;
    private JSONObject errors;

    public ServiceException(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public ServiceException(JSONObject errors) {
        this.errors = errors;
    }

    protected ServiceException(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getErrors() {
        return errors;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
    }
}
