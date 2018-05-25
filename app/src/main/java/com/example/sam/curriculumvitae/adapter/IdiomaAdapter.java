package com.example.sam.curriculumvitae.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sam.curriculumvitae.R;
import com.example.sam.curriculumvitae.activity.ActIdiomaOperation;
import com.example.sam.curriculumvitae.dominio.entidade.Idioma;

import java.util.List;

public class IdiomaAdapter extends RecyclerView.Adapter<IdiomaAdapter.ViewHolderIdioma> {
    private List<Idioma> dadosIdioma;

    public IdiomaAdapter(List<Idioma> dadosIdioma) {
        this.dadosIdioma = dadosIdioma;
    }

    @NonNull
    @Override
    public IdiomaAdapter.ViewHolderIdioma onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.layout_lista_idioma, parent, false);

        return new ViewHolderIdioma(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull IdiomaAdapter.ViewHolderIdioma holder, int position) {
        if (dadosIdioma != null && dadosIdioma.size() > 0) {
            Idioma idioma = dadosIdioma.get(position);

            holder.tvIdioma.setText(idioma.idioma);
            holder.tvNivel.setText(idioma.nivel);
        }
    }

    @Override
    public int getItemCount() {
        return dadosIdioma.size();
    }

    public class ViewHolderIdioma extends RecyclerView.ViewHolder {
        public TextView tvIdioma;
        public TextView tvNivel;

        private ViewHolderIdioma(View itemView, final Context context) {
            super(itemView);

            tvIdioma =  itemView.findViewById(R.id.tvIdioma);
            tvNivel =  itemView.findViewById(R.id.tvNivel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dadosIdioma.size() > 0) {
                        Idioma idioma=  dadosIdioma.get(getLayoutPosition());

                        Intent it = new Intent(context, ActIdiomaOperation.class);
                        it.putExtra("IDIOMA", idioma);
                        ((AppCompatActivity) context).startActivityForResult(it, 0);
                    }
                }
            });
        }
    }
}
