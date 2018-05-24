package com.example.sam.curriculumvitae.mensagem;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.sam.curriculumvitae.R;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Mensagem {

    public void alert(Context context, String titulo, String mensagem) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle(titulo);
        dlg.setMessage(mensagem);
        dlg.setNeutralButton(R.string.button_ok, null);
        dlg.show();
    }

    public void mostraTexto(Context context, String texto) {
        Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();
    }

    public void msgYesNo(Context context, String titulo, String mensagem, final Runnable runnable) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle(titulo);
        dlg.setMessage(mensagem);
        dlg.setPositiveButton(R.string.message_sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                runnable.run();
                dialog.dismiss();
            }
        });
        dlg.setNegativeButton(R.string.message_nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Nothing to do
            }
        });

        dlg.show();
    }
}
