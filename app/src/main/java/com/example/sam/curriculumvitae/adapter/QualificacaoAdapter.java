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
import com.example.sam.curriculumvitae.activity.ActQualificacaoOperation;
import com.example.sam.curriculumvitae.dominio.entidade.Qualificacao;

import java.util.List;

public class QualificacaoAdapter extends RecyclerView.Adapter<QualificacaoAdapter.ViewHolderQualificacao> {
    private List<Qualificacao> dadosQualificacao;

    public QualificacaoAdapter(List<Qualificacao> dadosQualificacao) {
        this.dadosQualificacao = dadosQualificacao;
    }

    @NonNull
    @Override
    public QualificacaoAdapter.ViewHolderQualificacao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.layout_lista_qualificacao, parent, false);

        return new ViewHolderQualificacao(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull QualificacaoAdapter.ViewHolderQualificacao holder, int position) {
        if (dadosQualificacao != null && dadosQualificacao.size() > 0) {
            Qualificacao qualificacao = dadosQualificacao.get(position);

            holder.tvAtividade.setText(qualificacao.atividade);
            holder.tvDescricao.setText(qualificacao.descricao);
        }
    }

    @Override
    public int getItemCount() {
        return dadosQualificacao.size();
    }

    public class ViewHolderQualificacao extends RecyclerView.ViewHolder {
        public TextView tvAtividade;
        public TextView tvDescricao;

        private ViewHolderQualificacao(View itemView, final Context context) {
            super(itemView);

            tvAtividade = itemView.findViewById(R.id.tvAtividade);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dadosQualificacao.size() > 0) {
                        Qualificacao qualificacao=  dadosQualificacao.get(getLayoutPosition());

                        Intent it = new Intent(context, ActQualificacaoOperation.class);
                        it.putExtra("QUALIFICACAO", qualificacao);
                        ((AppCompatActivity) context).startActivityForResult(it, 0);
                    }
                }
            });
        }
    }
}
