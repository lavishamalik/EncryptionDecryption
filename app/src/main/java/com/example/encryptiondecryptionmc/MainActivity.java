package com.example.encryptiondecryptionmc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    EditText inputText,password;
    TextView outputText;
    Button encrypt,decrypt;
    String outputString;
    String AES="AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText=findViewById(R.id.etInputText);
        password=findViewById(R.id.etPassword);
        outputText=findViewById(R.id.tvOutputText);
        encrypt=findViewById(R.id.btnEncrypt);
        decrypt=findViewById(R.id.btnDecrypt);

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputString=encryptText(inputText.getText().toString(),password.getText().toString());
                    outputText.setText(outputString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputString=decryptText(outputString,password.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                outputText.setText(outputString);
            }
        });
    }

    private String decryptText(final String outputString, final String password) throws Exception {
        SecretKeySpec secretKeySpec=generateKey(password);
        Cipher cipher=Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        byte[] decVal=Base64.decode(outputString,Base64.DEFAULT);
        byte[] decodeValue=cipher.doFinal(decVal);
        String decryptedString=new String(decodeValue);
        return decryptedString;
    }

    private String encryptText(final String data, final String password) throws Exception {
        SecretKeySpec secretKeySpec=generateKey(password);
        Cipher cipher=Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        byte[] encVal=cipher.doFinal(data.getBytes());
        String encryptedValue= Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(final String password) throws Exception{
        final MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
        byte[] bytes=password.getBytes("UTF-8");
        messageDigest.update(bytes,0,bytes.length);
        byte[] key=messageDigest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }
}