package com.example.edi.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;


public class MainActivity extends ActionBarActivity {


    //l'activité est créée
     //Invocado quando a activity é criada
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageView = (ImageView) findViewById(R.id.imagem);
        //Obtient l'emplacement où les photos sont stockées dans le dispositif de mémoire externe
        // Obtém o local onde as fotos são armazenadas na memória externa do dispositivo
        File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //Définit endroit où la photo sera enregistrée
        // Define o local completo onde a foto será armazenada (diretório + arquivo)
        this.imageFile = new File(picsDir, "foto.jpg");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

     // Demande code permettant d'identifier lorsque l'activity de la caméra est terminé
     // Código de requisição para poder identificar quando a activity da câmera é finalizada

            private static final int REQUEST_PICTURE = 1000;


     //View onde a foto tirada será exibida
     // View où l'image prise s'affiche

            private ImageView imageView;

      //où les photos seront stockés
      //Local de armazenamento da foto tirada

            private File imageFile;




            public void takePicture(View v){
                //Créer une intent qui sera utilisé pour ouvrir la caméra de l'application native
                // Cria uma intent que será usada para abrir a aplicação nativa de câmera
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Indique l'intent où la photo prise doit être stocké
                // Indica na intent o local onde a foto tirada deve ser armazenada
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));

                // Abre a aplicação de câmera
                //abre-se a aplicação da câmara
                startActivityForResult(i, REQUEST_PICTURE);
            }
            //Méthode appelée lorsque l'application native de la caméra est terminé
            // Método chamado quando a aplicação nativa da câmera é finalizada
            protected void onActivityResult(int requestCode, int resultCode, Intent data ){
                //il vérifie le code de demande et si le résultat est OK (autre résultat indique que
                // l'utilisateur a annulé le prendre en photo)
                // Verifica o código de requisição e se o resultado é OK (outro resultado indica que
                // o usuário cancelou a tirada da foto)
                if (requestCode == REQUEST_PICTURE && resultCode == RESULT_OK){

                    FileInputStream fis = null;

                    try{
                        try {
                            //il crée un FileInputStream pour lire l'image prise par la caméra
                            // Cria um FileInputStream para ler a foto tirada pela câmera
                            fis = new FileInputStream(imageFile);

                            //Convertit le stream en un objet Bitmap
                            // Converte a stream em um objeto Bitmap
                            Bitmap picture = BitmapFactory.decodeStream(fis);

                            //Affiche l'image bitmap dans la vue, pour l'utilisateur de voir la
                            // photo prise
                            // Exibe o bitmap na view, para que o usuário veja a foto tirada
                            imageView.setImageBitmap(picture);

                        }finally {
                            if (fis != null) {
                                fis.close();
                            }
                          }
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }




