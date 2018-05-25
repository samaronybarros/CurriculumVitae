package com.example.sam.curriculumvitae.curriculo;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.sam.curriculumvitae.R;
import com.example.sam.curriculumvitae.curriculo.pdfwriter.PDFWriter;
import com.example.sam.curriculumvitae.curriculo.pdfwriter.PaperSize;
import com.example.sam.curriculumvitae.curriculo.pdfwriter.StandardFonts;
import com.example.sam.curriculumvitae.database.DadosOpenHelper;
import com.example.sam.curriculumvitae.dominio.entidade.Curso;
import com.example.sam.curriculumvitae.dominio.entidade.Experiencia;
import com.example.sam.curriculumvitae.dominio.entidade.Formacao;
import com.example.sam.curriculumvitae.dominio.entidade.Idioma;
import com.example.sam.curriculumvitae.dominio.entidade.InfoPessoais;
import com.example.sam.curriculumvitae.dominio.entidade.Objetivo;
import com.example.sam.curriculumvitae.dominio.entidade.Qualificacao;
import com.example.sam.curriculumvitae.dominio.repositorio.CursoRepositorio;
import com.example.sam.curriculumvitae.dominio.repositorio.ExperienciaRepositorio;
import com.example.sam.curriculumvitae.dominio.repositorio.FormacaoRepositorio;
import com.example.sam.curriculumvitae.dominio.repositorio.IdiomaRepositorio;
import com.example.sam.curriculumvitae.dominio.repositorio.InfoPessoaisRepositorio;
import com.example.sam.curriculumvitae.dominio.repositorio.ObjetivoRepositorio;
import com.example.sam.curriculumvitae.dominio.repositorio.QualificacaoRepositorio;
import com.example.sam.curriculumvitae.mensagem.Mensagem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CriarPDF {
    private PDFWriter pdfWriter;
    private Mensagem mensagem;
    private String localArquivo;
    private String nomeArquivo;

    private InfoPessoais infoPessoais;
    private Objetivo objetivo;
    private List<Formacao> formacao;
    private List<Experiencia> experiencia;
    private List<Curso> curso;
    private List<Qualificacao> qualificacao;
    private List<Idioma> idioma;

    private SQLiteDatabase conexao;

    private Context context;

    public CriarPDF(Context context) {
        pdfWriter = new PDFWriter(PaperSize.FOLIO_WIDTH, PaperSize.FOLIO_HEIGHT);

        mensagem = new Mensagem();

        localArquivo = Environment.getExternalStorageDirectory().getAbsolutePath();

        this.context = context;
    }

    public String generatePDF() {
        String ret;
        int pageCount;
        int linha = 800;
        int salto;
        int prox = 0;

        int font10 = 10;
        int font18 = 18;
        int font20 = 20;

        montaEstruturaCurriculo();

        //Informações Pessoais
        if (infoPessoais != null) {
            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);
            pdfWriter.addText(100, linha, font20, infoPessoais.nome.toUpperCase());

            prox = 50;
            pdfWriter.addText(20, linha - prox, font10, context.getString(R.string.cv_endereco)); prox += 10;
            pdfWriter.addText(20, linha - prox, font10, context.getString(R.string.cv_nascimento)); prox += 10;
            pdfWriter.addText(20, linha - prox, font10, context.getString(R.string.cv_nacionalidade));
            prox = 50;
            pdfWriter.addText(300, linha - prox, font10, context.getString(R.string.cv_telefone)); prox += 10;
            pdfWriter.addText(300, linha - prox, font10, context.getString(R.string.cv_celular)); prox += 10;
            pdfWriter.addText(300, linha - prox, font10, context.getString(R.string.cv_email));

            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_ROMAN, StandardFonts.WIN_ANSI_ENCODING);

            prox = 50;
            pdfWriter.addText(100, linha - prox, font10, infoPessoais.endereco); prox += 10;
            pdfWriter.addText(100, linha - prox, font10, infoPessoais.dataNascimento); prox += 10;
            pdfWriter.addText(100, linha - prox, font10, infoPessoais.nacionalidade);
            prox = 50;
            pdfWriter.addText(380, linha - prox, font10, infoPessoais.telefoneFixo); prox += 10;
            pdfWriter.addText(380, linha - prox, font10, infoPessoais.telefoneCelular); prox += 10;
            pdfWriter.addText(380, linha - prox, font10, infoPessoais.email);
        }

        //Objetivo
        if (objetivo != null) {
            prox += 30;
            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);

            pdfWriter.addText(20, linha - prox, font18, context.getString(R.string.cv_objetivo)); prox += 10;
            pdfWriter.addLine(20, linha - prox, 540, linha - prox);

            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_ROMAN, StandardFonts.WIN_ANSI_ENCODING);

            prox += 20;
            if (objetivo.descricao.length() < 100) {
                pdfWriter.addText(20, linha - prox, font10, objetivo.descricao.substring(0, objetivo.descricao.length()));
            } else if (objetivo.descricao.length() < 200) {
                pdfWriter.addText(20, linha - prox, font10, objetivo.descricao.substring(0, 100)); prox += 10;
                pdfWriter.addText(20, linha - prox, font10, objetivo.descricao.substring(101, objetivo.descricao.length()));
            } else if (objetivo.descricao.length() < 300) {
                pdfWriter.addText(20, linha - prox, font10, objetivo.descricao.substring(0, 100)); prox += 10;
                pdfWriter.addText(20, linha - prox, font10, objetivo.descricao.substring(101, 200)); prox += 10;
                pdfWriter.addText(20, linha - prox, font10, objetivo.descricao.substring(201, objetivo.descricao.length()));
            }
        }

        //Formação
        if (formacao != null) {
            prox += 30;
            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);

            pdfWriter.addText(20, linha - prox, font18, context.getString(R.string.cv_formacao)); prox += 10;
            pdfWriter.addLine(20, linha - prox, 540, linha - prox);

            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_ROMAN, StandardFonts.WIN_ANSI_ENCODING);

            prox += 10;
            salto = 0;
            for (int i = 0; i < formacao.size(); i++) {
                salto += 20;
                pdfWriter.addText(20, linha - prox - salto, font10, formacao.get(i).dataInicio);
                pdfWriter.addText(80, linha - prox - salto, font10, "-");
                if (formacao.get(i).isDataConclusaoVazia()) {
                    pdfWriter.addText(100, linha - prox - salto, font10, "Cursando");
                } else {
                    pdfWriter.addText(100, linha - prox - salto, font10, formacao.get(i).dataConclusao);
                }
                pdfWriter.addText(175, linha - prox - salto, font10, formacao.get(i).instituicao); prox += 10;
                pdfWriter.addText(175, linha - prox - salto, font10, formacao.get(i).curso);
            }

            linha -= salto;
        }

        //Experiência
        if (experiencia != null) {
            prox += 30;
            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);

            pdfWriter.addText(20, linha - prox, font18, context.getString(R.string.cv_experiencia)); prox += 10;
            pdfWriter.addLine(20, linha - prox, 540, linha - prox);

            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_ROMAN, StandardFonts.WIN_ANSI_ENCODING);

            prox += 10;
            salto = 0;
            for (int i = 0; i < experiencia.size(); i++) {
                salto += 20;
                pdfWriter.addText(20, linha - prox - salto, font10, experiencia.get(i).dataInicio);
                pdfWriter.addText(80, linha - prox - salto, font10, "-");
                if (experiencia.get(i).isDataFimVazia()) {
                    pdfWriter.addText(100, linha - prox - salto, font10, "Presente");
                } else {
                    pdfWriter.addText(100, linha - prox - salto, font10, experiencia.get(i).dataFim);
                }
                pdfWriter.addText(175, linha - prox - salto, font10, experiencia.get(i).empresa); prox += 10;
                pdfWriter.addText(175, linha - prox - salto, font10, experiencia.get(i).cargo);
                prox += 10;
                if (experiencia.get(i).atividades.length() < 70) {
                    pdfWriter.addText(175, linha - prox - salto, font10, experiencia.get(i).atividades.substring(0, experiencia.get(i).atividades.length()));
                } else if (experiencia.get(i).atividades.length() < 140) {
                    pdfWriter.addText(175, linha - prox - salto, font10, experiencia.get(i).atividades.substring(0, 70)); prox += 10;
                    pdfWriter.addText(175, linha - prox - salto, font10, experiencia.get(i).atividades.substring(71, experiencia.get(i).atividades.length()));
                } else if (experiencia.get(i).atividades.length() < 210) {
                    pdfWriter.addText(175, linha - prox - salto, font10, experiencia.get(i).atividades.substring(0, 70)); prox += 10;
                    pdfWriter.addText(175, linha - prox - salto, font10, experiencia.get(i).atividades.substring(71, 140)); prox += 10;
                    pdfWriter.addText(175, linha - prox - salto, font10, experiencia.get(i).atividades.substring(141, experiencia.get(i).atividades.length()));
                } else if (experiencia.get(i).atividades.length() < 280) {
                    pdfWriter.addText(175, linha - prox - salto, font10, experiencia.get(i).atividades.substring(0, 70)); prox += 10;
                    pdfWriter.addText(175, linha - prox - salto, font10, experiencia.get(i).atividades.substring(71, 140)); prox += 10;
                    pdfWriter.addText(175, linha - prox - salto, font10, experiencia.get(i).atividades.substring(141, 210)); prox += 10;
                    pdfWriter.addText(175, linha - prox - salto, font10, experiencia.get(i).atividades.substring(211, experiencia.get(i).atividades.length()));
                }
            }

            linha -= salto;
        }

        //Cursos
        if (curso != null) {
            prox += 30;
            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);

            pdfWriter.addText(20, linha - prox, font18, context.getString(R.string.cv_curso)); prox += 10;
            pdfWriter.addLine(20, linha - prox, 540, linha - prox);

            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_ROMAN, StandardFonts.WIN_ANSI_ENCODING);

            prox += 10;
            salto = 0;
            for (int i = 0; i < curso.size(); i++) {
                salto += 20;
                pdfWriter.addText(20, linha - prox - salto, font10, curso.get(i).dataInicio);
                pdfWriter.addText(80, linha - prox - salto, font10, "-");
                if (curso.get(i).isDataConclusaoVazia()) {
                    pdfWriter.addText(100, linha - prox - salto, font10, "Cursando");
                } else {
                    pdfWriter.addText(100, linha - prox - salto, font10, curso.get(i).dataConclusao);
                }
                pdfWriter.addText(175, linha - prox - salto, font10, curso.get(i).instituicao); prox += 10;
                pdfWriter.addText(175, linha - prox - salto, font10, curso.get(i).curso);
            }

            linha -= salto;
        }

        //Qualificação
        if (qualificacao != null) {
            prox += 30;
            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);

            pdfWriter.addText(20, linha - prox, font18, context.getString(R.string.cv_qualificacao)); prox += 10;
            pdfWriter.addLine(20, linha - prox, 540, linha - prox);

            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_ROMAN, StandardFonts.WIN_ANSI_ENCODING);

            prox += 10;
            salto = 0;
            for (int i = 0; i < qualificacao.size(); i++) {
                salto += 20;
                pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);
                pdfWriter.addText(20, linha - prox - salto, font10, qualificacao.get(i).atividade);
                pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_ROMAN, StandardFonts.WIN_ANSI_ENCODING);
                pdfWriter.addText(100, linha - prox - salto, font10, qualificacao.get(i).descricao);
            }

            linha -= salto;
        }

        //Idioma
        if (idioma != null) {
            prox += 30;
            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);

            pdfWriter.addText(20, linha - prox, font18, context.getString(R.string.cv_idioma)); prox += 10;
            pdfWriter.addLine(20, linha - prox, 540, linha - prox);

            pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_ROMAN, StandardFonts.WIN_ANSI_ENCODING);

            prox += 10;
            salto = 0;
            for (int i = 0; i < idioma.size(); i++) {
                salto += 20;
                pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);
                pdfWriter.addText(20, linha - prox - salto, font10, idioma.get(i).idioma);
                pdfWriter.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_ROMAN, StandardFonts.WIN_ANSI_ENCODING);
                pdfWriter.addText(100, linha - prox - salto, font10, idioma.get(i).nivel);
            }
        }

        pageCount = pdfWriter.getPageCount();
        for (int i = 0; i < pageCount; i++) {
            pdfWriter.setCurrentPage(i);
            pdfWriter.addText(500, 10, 8, Integer.toString(i + 1) + " / " + Integer.toString(pageCount));
        }

        ret = pdfWriter.asString();

        return ret;
    }

    public void outputToFile(String fileName, String pdfContent, String encoding) {
        nomeArquivo = localArquivo + "/" + fileName;

        File newFile = new File(nomeArquivo);
        try {
            newFile.createNewFile();
            try {
                FileOutputStream pdfFile = new FileOutputStream(newFile);
                pdfFile.write(pdfContent.getBytes(encoding));
                pdfFile.close();
            } catch(FileNotFoundException e) {
                mensagem.alert(context, "FileNotFoundException", e.getMessage());
            }
        } catch(IOException e) {
            mensagem.alert(context, "IOException", e.getMessage());
        }
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    private void montaEstruturaCurriculo() {
        criarConexao();

        getInfoPessoais();
        getObjetivo();
        getFormacao();
        getExperiencia();
        getCurso();
        getQualificacao();
        getIdioma();
    }

    private void criarConexao() {
        try {
            DadosOpenHelper dadosOpenHelper = new DadosOpenHelper(context);
            conexao = dadosOpenHelper.getWritableDatabase();
        } catch (SQLException ex) {
            mensagem.alert(context, context.getString(R.string.message_erro), ex.getMessage());
        }
    }

    private void getInfoPessoais() {
        InfoPessoaisRepositorio infoPessoaisRepositorio;

        infoPessoais = new InfoPessoais();
        infoPessoaisRepositorio = new InfoPessoaisRepositorio(conexao);

        infoPessoais = infoPessoaisRepositorio.buscar();
    }

    private void getObjetivo() {
        ObjetivoRepositorio objetivoRepositorio;

        objetivo = new Objetivo();
        objetivoRepositorio = new ObjetivoRepositorio(conexao);

        objetivo = objetivoRepositorio.buscar();
    }

    private void getFormacao() {
        FormacaoRepositorio formacaoRepositorio;

        formacao = new ArrayList<>();
        formacaoRepositorio = new FormacaoRepositorio(conexao);

        formacao = formacaoRepositorio.buscarTodasFormacoes();
    }

    private void getExperiencia() {
        ExperienciaRepositorio experienciaRepositorio;

        experiencia = new ArrayList<>();
        experienciaRepositorio = new ExperienciaRepositorio(conexao);

        experiencia = experienciaRepositorio.buscarTodasExperiencias();
    }

    private void getCurso() {
        CursoRepositorio cursoRepositorio;

        curso = new ArrayList<>();
        cursoRepositorio = new CursoRepositorio(conexao);

        curso = cursoRepositorio.buscarTodosCursos();
    }

    private void getQualificacao() {
        QualificacaoRepositorio qualificacaoRepositorio;

        qualificacao = new ArrayList<>();
        qualificacaoRepositorio = new QualificacaoRepositorio(conexao);

        qualificacao = qualificacaoRepositorio.buscarTodasQualificacoes();
    }

    private void getIdioma() {
        IdiomaRepositorio idiomaRepositorio;

        idioma = new ArrayList<>();
        idiomaRepositorio = new IdiomaRepositorio(conexao);

        idioma = idiomaRepositorio.buscarTodosIdiomas();
    }
}
