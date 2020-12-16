package com.velozion.myoitc.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class Listeners {
	/**
	 * FileUploadListener
	 * @author User
	 *
	 */
	public interface FileUploadListener {
		void onFileUploaded(JSONObject newJsObj);
	}
	/**
	 * GETMethodListener
	 * @author User
	 *
	 */
	public interface GETMethodListener {
		void onGetCompleted(JSONObject newJsObj, int msgId);
		void onGetCanceled();
	}
	/**
	 * POSTMethodListener
	 * @author User
	 *
	 */
	public interface POSTMethodListener {
		void onPostCompleted(JSONObject newJsObj, int msgId) throws JSONException;

	}

	public interface onResponse{

	}

	public interface PostMethodErrorListener{
		void onErrorResponse(VolleyError error);
	}

}
