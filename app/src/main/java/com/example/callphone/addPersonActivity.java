package com.example.callphone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class addPersonActivity extends AppCompatActivity {
    final int REQUEST_CODE_PICK_PHOTO=1;
    FrameLayout pickPhotoPerson;
    ImageView photoPerson;
    TextView firstName;
    TextView lastName;
    TextView phone;
    TextView luu;
    Toolbar toolbar;
    ArrayList<ContentProviderOperation> ops = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    90);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_CONTACTS},
                    100);
        }
        pickPhotoPerson=findViewById(R.id.pickpicture);
        photoPerson=findViewById(R.id.photoPerson);
        firstName=findViewById(R.id.firstname);
        lastName=findViewById(R.id.lastname);
        phone=findViewById(R.id.phone);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();

                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
        luu=findViewById(R.id.luu);
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact();
            }
        });


    }

    public void addContact(){


        int rawContactID = ops.size();
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,
                        null).withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(
                        ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        firstName.getText().toString()+" "+lastName.getText().toString()).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(
                        ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone.getText().toString())
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());

        try {
            // Executing all the insert operations as a single database
            // transaction
            getContentResolver().applyBatch(ContactsContract.AUTHORITY,
                    ops);
            Intent intent=new Intent();

            setResult(RESULT_OK,intent);
            finish();

        } catch (RemoteException e) {
            Intent intent=new Intent();

            setResult(RESULT_CANCELED,intent);
            finish();
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            Intent intent=new Intent();

            setResult(RESULT_CANCELED,intent);
            finish();
            e.printStackTrace();
        }

    }
    private void selectImage() {

        final CharSequence[] options = {"Chọn từ Album",
                "Hủy" };

        AlertDialog.Builder builder = new AlertDialog.Builder(addPersonActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Chọn từ Album")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

}
