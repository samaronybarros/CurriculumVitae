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
import com.example.sam.curriculumvitae.activity.ActCursoOperation;
import com.example.sam.curriculumvitae.dominio.entidade.Curso;

import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.ViewHolderCurso> {
    private List<Curso> dadosCurso;

    public CursoAdapter(List<Curso> dadosCurso) {
        this.dadosCurso = dadosCurso;
    }

    @NonNull
    @Override
    public CursoAdapter.ViewHolderCurso onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.layout_lista_curso, parent, false);

        return new ViewHolderCurso(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull CursoAdapter.ViewHolderCurso holder, int position) {
        if (dadosCurso != null && dadosCurso.size() > 0) {
            Curso curso = dadosCurso.get(position);

            holder.tvCurso.setText(curso.curso);
            holder.tvInstituicao.setText(curso.instituicao);
        }
    }

    @Override
    public int getItemCount() {
        return dadosCurso.size();
    }

    public class ViewHolderCurso extends RecyclerView.ViewHolder {
        public TextView tvCurso;
        public TextView tvInstituicao;

        private ViewHolderCurso(View itemView, final Context context) {
            super(itemView);

            tvCurso = itemView.findViewById(R.id.tvCurso);
            tvInstituicao = itemView.findViewById(R.id.tvInstituicao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dadosCurso.size() > 0) {
                        Curso curso=  dadosCurso.get(getLayoutPosition());

                        Intent it = new Intent(context, ActCursoOperation.class);
                        it.putExtra("CURSO", curso);
                        ((AppCompatActivity) context).startActivityForResult(it, 0);
                    }
                }
            });
        }
    }
}
