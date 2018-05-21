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
import com.example.sam.curriculumvitae.activity.ActExperienciaOperation;
import com.example.sam.curriculumvitae.dominio.entidade.Experiencia;

import java.util.List;

public class ExperienciaAdapter extends RecyclerView.Adapter<ExperienciaAdapter.ViewHolderExperiencia> {
    private List<Experiencia> dadosExperiencia;

    public ExperienciaAdapter(List<Experiencia> dadosExperiencia) {
        this.dadosExperiencia = dadosExperiencia;
    }

    @NonNull
    @Override
    public ExperienciaAdapter.ViewHolderExperiencia onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.layout_lista_experiencia, parent, false);

        ViewHolderExperiencia viewHolderExperiencia = new ViewHolderExperiencia(view, parent.getContext());

        return viewHolderExperiencia;
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienciaAdapter.ViewHolderExperiencia holder, int position) {
        if (dadosExperiencia != null && dadosExperiencia.size() > 0) {
            Experiencia experiencia = dadosExperiencia.get(position);

            holder.tvEmpresa.setText(experiencia.empresa);
            holder.tvCargo.setText(experiencia.cargo);
        }
    }

    @Override
    public int getItemCount() {
        return dadosExperiencia.size();
    }

    public class ViewHolderExperiencia extends RecyclerView.ViewHolder {
        public TextView tvEmpresa;
        public TextView tvCargo;

        public ViewHolderExperiencia(View itemView, final Context context) {
            super(itemView);

            tvEmpresa = (TextView) itemView.findViewById(R.id.tvEmpresa);
            tvCargo = (TextView) itemView.findViewById(R.id.tvCargo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dadosExperiencia.size() > 0) {
                        Experiencia experiencia=  dadosExperiencia.get(getLayoutPosition());

                        Intent it = new Intent(context, ActExperienciaOperation.class);
                        it.putExtra("EXPERIENCIA", experiencia);
                        ((AppCompatActivity) context).startActivityForResult(it, 0);
                    }
                }
            });
        }
    }
}
