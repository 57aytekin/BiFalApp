package com.example.bifal.activity.kahveFali;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bifal.activity.KahveHakkiActivity;
import com.example.bifal.R;
import com.example.bifal.activity.YorumlaniyorActivity;
import com.example.bifal.activity.anaSayfa.AnaSayfaActivity;
import com.example.bifal.manager.SessionManager;
import com.example.bifal.model.User;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class KahveFaliActivity extends AppCompatActivity implements View.OnClickListener, KahveFaliView {
    private ImageView btnPhoto1, btnPhoto2, btnPhoto3;
    private Button btnGonder, btnNiyetime;
    private EditText etAdSoyad, etYas;
    private SessionManager sessionManager;
    private Spinner spIsDurumu, spMedeniDurum,spIliskiDurum;
    final Calendar myCalendar = Calendar.getInstance();
    Integer REQUEST_CAMERA =1, SELECT_FILE = 0;
    int whichPhoto = 0;
    int niyetineIcSeciliMi = 0, photo1SeciliMi=0, photo2SeciliMi=0, photo3SeciliMi=0, photoAuto = 0;
    private KahveFaliPresenter presenter;
    private Bitmap bitmap1, bitmap2, bitmap3;
    private ProgressDialog progressDialog;


    String[] array_isDurumu = new String[]{"İş Durumunuz", "Çalışıyor", "Çalışmıyor", "Öğrenci", "Ev Hanımı"};
    String[] array_medeniDurum = new String[]{"Medeni Durumunuz", "Evli", "Bekar", "Dul"};
    String[] array_iliskiDurumu = new String[]{"İlişki Durumunuz","Platonik","Yok","Aşık"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kahve_fali);
        uiElement();

        HashMap<String, String> user = sessionManager.userDetail();
        String adSoyad = user.get(sessionManager.FIRST_NAME)+" "+user.get(sessionManager.LAST_NAME);
        String web_id = user.get(sessionManager.USERID);
        presenter.getUser(web_id);

        etAdSoyad.setText(adSoyad);
        btnNiyetime.setOnClickListener(this);
        btnPhoto1.setOnClickListener(this);
        btnPhoto2.setOnClickListener(this);
        btnPhoto3.setOnClickListener(this);


        fillSpinnerIsDurumu(array_isDurumu,spIsDurumu);
        fillSpinnerIsDurumu(array_medeniDurum,spMedeniDurum);
        fillSpinnerIsDurumu(array_iliskiDurumu,spIliskiDurum);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        etYas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(KahveFaliActivity.this, date , myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNiyetime:
                niyetineIcSeciliMi = 1;
                photo1SeciliMi = 1;
                photo2SeciliMi = 1;
                photo3SeciliMi = 1;
                photoAuto = 1;
                Random random = new Random();
                ImageView[] image = {btnPhoto1, btnPhoto2, btnPhoto3};
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.from_bottom_icon);

                for (int i=0; i<3; i++){
                    int randonSayi = random.nextInt((9-1)+1)+1;
                    String url = "https://aytekinccomez.000webhostapp.com/phpFile/kahve_image/"+randonSayi+".jpg";
                    image[i].setAnimation(animation);
                    Picasso.get().load(url).centerCrop().resize(image[i].getMeasuredWidth(), image[i].getMeasuredHeight()).into(image[i]);
                }
                break;
            case R.id.btnFoto1:
                whichPhoto = 1;
                photo1SeciliMi = 1;
                niyetineIcSeciliMi = 1;
                selectImage();
                break;
            case  R.id.btnFoto2:
                photo2SeciliMi = 1;
                niyetineIcSeciliMi = 1;
                whichPhoto = 2;
                selectImage();
                break;
            case R.id.btnFoto3:
                photo3SeciliMi = 1;
                niyetineIcSeciliMi = 1;
                whichPhoto = 3;
                selectImage();
                break;
        }
    }



    public void uiElement(){
        etAdSoyad = findViewById(R.id.etKahveFaliAdSoyad);
        etYas = findViewById(R.id.etKahveFaliYas);
        btnPhoto1 = findViewById(R.id.btnFoto1);
        btnPhoto2 = findViewById(R.id.btnFoto2);
        btnPhoto3 = findViewById(R.id.btnFoto3);
        btnGonder = findViewById(R.id.btnKahveFaliGonder);
        btnNiyetime = findViewById(R.id.btnNiyetime);
        spIsDurumu = findViewById(R.id.spKahveFaliIsDurumu);
        spIliskiDurum =findViewById(R.id.spKahveFaliIliski);
        spMedeniDurum = findViewById(R.id.spKahveFaliMedeniD);
        sessionManager = new SessionManager(getApplicationContext());
        presenter = new KahveFaliPresenter(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Bekleyiniz");
        progressDialog.setCancelable(false);
    }
    public void fillSpinnerIsDurumu(String[] array, final Spinner spinner){

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, array);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorEdittext));
                } else {
                    ((TextView) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etYas.setText(sdf.format(myCalendar.getTime()));
    }
    private void selectImage(){
        final CharSequence[] items ={"Camera","Galery","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals("Camera")){

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }else if(items[which].equals("Galery")){

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"), SELECT_FILE );
                }else if(items[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private boolean kontrolEt() {
        return niyetineIcSeciliMi != 0 && photo1SeciliMi != 0 && photo2SeciliMi != 0 && photo3SeciliMi != 0
                && !etAdSoyad.getText().toString().equals("") && spIsDurumu.getSelectedItemId() != 0
                && spIliskiDurum.getSelectedItemId() != 0 && spMedeniDurum.getSelectedItemId() != 0
                && !etYas.getText().toString().equals("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                if(data != null){
                    Bundle bundle = data.getExtras();
                    bitmap1 = (Bitmap) bundle.get("data");
                    bitmap2 = (Bitmap) bundle.get("data");
                    bitmap3 = (Bitmap) bundle.get("data");
                    if(whichPhoto == 1){
                        btnPhoto1.setImageBitmap(bitmap1);

                    }else if(whichPhoto == 2){
                        btnPhoto2.setImageBitmap(bitmap2);
                    }else if(whichPhoto == 3){
                        btnPhoto3.setImageBitmap(bitmap3);
                    }
                }
            }else if(requestCode == SELECT_FILE){
                if(data != null){
                    Uri selectImageUri  = data.getData();

                    if(whichPhoto == 1){
                        try {
                            bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectImageUri);
                            btnPhoto1.setImageBitmap(bitmap1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(whichPhoto == 2){
                        try {
                            bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectImageUri);
                            btnPhoto2.setImageBitmap(bitmap2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(whichPhoto == 3){
                        try {
                            bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectImageUri);
                            btnPhoto3.setImageBitmap(bitmap3);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    @Override
    public void onSucees(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getUser(final List<User> onGetResult) {
        String name = onGetResult.get(0).getName()+" "+onGetResult.get(0).getSurname();
        String is = onGetResult.get(0).getIs_durumu();
        String medeni = onGetResult.get(0).getMedeni_durum();
        String iliski = onGetResult.get(0).getIliski_durumu();
        String yas = onGetResult.get(0).getYas_tarih();
        final int cafeCount = onGetResult.get(0).getKahve_hakki();
        etAdSoyad.setText(name);
        if(onGetResult.get(0).getIliski_durumu() == null){
            setSpinText(spIliskiDurum,"İlişki Durumunuz");
        }else if(onGetResult.get(0).getIs_durumu() == null){
            setSpinText(spIsDurumu,"İş Durumunuz");
        }else if(onGetResult.get(0).getMedeni_durum() == null){
            setSpinText(spMedeniDurum,"Medeni Durumunuz");
        }else if(onGetResult.get(0).getYas_tarih() == null){
            etYas.setHint("Yasiniz");
        }
        else{
            setSpinText(spIsDurumu, is);
            setSpinText(spMedeniDurum,medeni);
            setSpinText(spIliskiDurum, iliski);
            etYas.setText(yas);
        }


        btnGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] nameSurname = etAdSoyad.getText().toString().split(" ");
                String name = nameSurname[0];
                String surname = nameSurname[1];
                String is_durumu = spIsDurumu.getSelectedItem().toString();
                String medeni_durum = spMedeniDurum.getSelectedItem().toString();
                String iliski_durumu = spIliskiDurum.getSelectedItem().toString();
                String yas = etYas.getText().toString();
                HashMap<String, String> user = sessionManager.userDetail();
                String web_id = user.get(sessionManager.USERID);
                Random random = new Random();
                int randonSayi = random.nextInt((3-1)+1)+1;

                int id = onGetResult.get(0).getId();
                String name1 = onGetResult.get(0).getName()+"1";
                String name2 = onGetResult.get(0).getName()+"2";
                String name3 = onGetResult.get(0).getName()+"3";



                if(kontrolEt()){
                    if(photoAuto == 1){
                        if(cafeCount > 0){
                            presenter.update_user(web_id, name,surname, is_durumu, medeni_durum, iliski_durumu, yas);
                            presenter.saveUserFal(id, randonSayi);
                            //Kahve Hakkını 1 azaltıyoruz.
                            int newCafeCount = (onGetResult.get(0).getKahve_hakki())-1;
                            //Veritabanındaki kahve hakkini güncelliyoruz.
                            presenter.update_kahve_hakki(onGetResult.get(0).getId(), newCafeCount);
                            startActivity(new Intent(KahveFaliActivity.this, YorumlaniyorActivity.class));
                            finish();
                        }else{
                            startActivity(new Intent(KahveFaliActivity.this, KahveHakkiActivity.class));
                            finish();
                        }

                    }else{
                        if(cafeCount > 0){
                            presenter.update_user(web_id, name,surname, is_durumu, medeni_durum, iliski_durumu, yas);
                            //Fotografları veritabanına kaydedeceğiz.
                            String image1 = imageToString(bitmap1);
                            String image2 = imageToString(bitmap2);
                            String image3 = imageToString(bitmap3);

                            //Kahve hakkini 1 azaltıyoruz..
                            int newCafeCount = (onGetResult.get(0).getKahve_hakki())-1;
                            //Veritabanındaki kahve hakkini güncelliyoruz.
                            presenter.update_kahve_hakki(onGetResult.get(0).getId(), newCafeCount);

                            presenter.upload_image(id, name1, name2, name3, image1, image2, image3);
                            presenter.saveUserFal(id, randonSayi);
                            startActivity(new Intent(KahveFaliActivity.this, YorumlaniyorActivity.class));
                            finish();
                        }else{
                            startActivity(new Intent(KahveFaliActivity.this, KahveHakkiActivity.class));
                            finish();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Lütfen bütün alanları doldurduğunuzdan ve resim seçtiğinizden emin olun!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void showLoadin() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    public void setSpinText(Spinner spin, String text)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().contains(text))
            {
                spin.setSelection(i);
            }
        }

    }

}
