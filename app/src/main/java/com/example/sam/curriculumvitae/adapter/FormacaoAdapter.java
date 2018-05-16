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
import com.example.sam.curriculumvitae.activity.ActFormacaoOperation;
import com.example.sam.curriculumvitae.dominio.entidade.Formacao;

import java.util.List;

public class FormacaoAdapter extends RecyclerView.Adapter<FormacaoAdapter.ViewHolderFormacao> {
    private List<Formacao> dadosFormacao;

    public FormacaoAdapter(List<Formacao> dadosFormacao) {
        this.dadosFormacao = dadosFormacao;
    }

    @NonNull
    @Override
    public FormacaoAdapter.ViewHolderFormacao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.layout_lista_formacao, parent, false);

        ViewHolderFormacao viewHolderFormacao = new ViewHolderFormacao(view, parent.getContext());

        return viewHolderFormacao;
    }

    @Override
    public void onBindViewHolder(@NonNull FormacaoAdapter.ViewHolderFormacao holder, int position) {
        if (dadosFormacao != null && dadosFormacao.size() > 0) {
            Formacao formacao = dadosFormacao.get(position);

            holder.tvFormacao.setText(formacao.curso);
            holder.tvInstituicao.setText(formacao.instituicao);
        }
    }

    @Override
    public int getItemCount() {
        return dadosFormacao.size();
    }

    public class ViewHolderFormacao extends RecyclerView.ViewHolder {
        public TextView tvFormacao;
        public TextView tvInstituicao;

        public ViewHolderFormacao(View itemView, final Context context) {
            super(itemView);

            tvFormacao = (TextView) itemView.findViewById(R.id.tvFormacao);
            tvInstituicao = (TextView) itemView.findViewById(R.id.tvInstituicao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dadosFormacao.size() > 0) {
                        Formacao formacao=  dadosFormacao.get(getLayoutPosition());

                        Intent it = new Intent(context, ActFormacaoOperation.class);
                        it.putExtra("FORMACAO", formacao);
                        ((AppCompatActivity) context).startActivityForResult(it, 0);
                    }
                }
            });
        }
    }
}
