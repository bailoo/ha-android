package com.mycompany.myinventory;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mobilemerit.controller.WebServiceController;
import com.mobilemerit.database.DatabaseHandler;
import com.mobilemerit.javafiles.LoginResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    //   private boolean loginStatus = false;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private UserLoginTask mAuthTask = null;
    //public static final String MyPREFERENCES = "MyPrefs";
//    public static final String userID = "userIDKey";
//    public static final String phoneNo = "phoneNoKey";

    SharedPreferences preferences;

    // UI references.
    private AutoCompleteTextView mPhoneView;
    private EditText mOTPView;
    private View mProgressView;
    private View mLoginFormView;
    private int mStatusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mPhoneView = (AutoCompleteTextView) findViewById(R.id.phone);
        populateAutoComplete();

        //preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mOTPView = (EditText) findViewById(R.id.password);
        mOTPView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        boolean cancelLogin = false;
        View focusView = null;

        // Reset errors.
        mPhoneView.setError(null);
        mOTPView.setError(null);

        // Store values at the time of the login attempt.
        String phone = mPhoneView.getText().toString();
        String otp = mOTPView.getText().toString();

        // Check for a valid OTP, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//        mOTPView.setError(getString(R.string.error_invalid_password));
//            focusView = mOTPView;
//            cancel = true;
//    }

        // Check for a valid email address.
        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancelLogin = true;
        } else if (!isIDValid(phone)) {
            mPhoneView.setError(getString(R.string.error_invalid_email));
            focusView = mPhoneView;
            cancelLogin = true;
        }
        if (cancelLogin) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            doLogin();


//            // Call webservice & send the login id & pwd.
//            if (doLogin()) {
//
//                System.out.println("Validate login is true");
//                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                finish();
//
//                //  finish();
//            } else {
//                mOTPView.setError(getString(R.string.error_incorrect_password));
//                mOTPView.requestFocus();
//            }
//
//            // mAuthTask = new UserLoginTask(email, password);
//            // mAuthTask.execute((Void) null);
        }
    }

    private void doLogin() {

        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";
        //String url = "http://api.androidhive.info/volley/person_object.json";
        String url = getString(R.string.loginURL, mPhoneView.getText().toString());

        //System.out.println("Show URL :" + url);


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: Validate Response.

                if (mStatusCode == 200) {

                    Gson gson = new Gson();
                    LoginResponse loginResponse = gson.fromJson(response.toString(), LoginResponse.class);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString(getString(R.string.preferencesUSERID), loginResponse.getId());
                    editor.putString(getString(R.string.preferencesPhoneNo), loginResponse.getMobile());

                    editor.commit();

                    //Log.d(TAG, response.toString());
                    //pDialog.hide();
                    System.out.println(response.toString());
                    //loginStatus = true;
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    showProgress(false);
                    finish();
                } else if (mStatusCode == 204) {
                    showProgress(false);
//                    mOTPView.setError(getString(R.string.error_incorrect_password));
//                    mOTPView.requestFocus();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Some error :" + error.toString());
                showProgress(false);
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                // hide the progress dialog
//                pDialog.hide();

            }
        }) {

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                //System.out.println("Response Status Code is : " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };

        // Adding request to request queue
        WebServiceController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


    }

    private boolean isIDValid(String email) {
        //TODO: Replace this with your own logic to Validate Phone number.
        return true;
    }

    private boolean isPasswordValid(String otp) {
        //TODO: Replace this with your own logic
        return otp.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mPhoneView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//        private final String mEmail;
//        private final String mPassword;
//
//        UserLoginTask(String email, String password) {
//            mEmail = email;
//            mPassword = password;
//        }

//        @Override
//        protected Boolean doInBackground(Void... params) {
//            // TODO: attempt authentication against a network service.
//
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }
//
//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }
//
//            // TODO: register the new account here.
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//
//            if (success) {
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                finish();
//
//                //  finish();
//            } else {
//                mOTPView.setError(getString(R.string.error_incorrect_password));
//                mOTPView.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }
}