package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.lucianoalbuquerque18gmail.app.simplelist0110.Add_RelacaoLisTare;
import com.lucianoalbuquerque18gmail.app.simplelist0110.Add_RelacaoList;
import com.lucianoalbuquerque18gmail.app.simplelist0110.List_t;
import com.lucianoalbuquerque18gmail.app.simplelist0110.R;
import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.List_DeL_Tarefa;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Lista_De_List;

import java.util.List;

public class ListaDeLTarfAdapter extends RecyclerView.Adapter<ListaDeLTarfAdapter.ViewHolderListadeLTaref> {

    private ListaDeLTarfAdapter listaDeLTarfAdapter;
    private DataBaseLista dataBaseLista;
    private SQLiteDatabase conexaoList;
    private List<List_DeL_Tarefa> rela2;
    private List_DeL_Tarefa list_DeL_Tarefa;

    public ListaDeLTarfAdapter(List<List_DeL_Tarefa> rela2){

        this.rela2 = rela2;

    }

    public void atualizarNomeListT(List_DeL_Tarefa list_DeL_Tarefa){
        int position = rela2.indexOf(list_DeL_Tarefa);
        this.notifyItemChanged(position);
       // rela2.set(rela2.indexOf(list_DeL_Tarefa), list_DeL_Tarefa);
        //notifyItemChanged(rela2.indexOf(list_DeL_Tarefa));
    }

    public void adicionarCliente(List_DeL_Tarefa list_DeL_Tarefa){
        rela2.add(list_DeL_Tarefa);
        notifyItemInserted(getItemCount());
    }

    public void removerCliente(List_DeL_Tarefa list_DeL_Tarefa){
        int position = rela2.indexOf(list_DeL_Tarefa);
        rela2.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ListaDeLTarfAdapter.ViewHolderListadeLTaref onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.linha_de_ltarefa, parent, false);
        ViewHolderListadeLTaref viewHolderListadeLTaref = new ViewHolderListadeLTaref(view,parent.getContext());

        return viewHolderListadeLTaref;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaDeLTarfAdapter.ViewHolderListadeLTaref holder, int position) {

        if ((rela2 != null) && (rela2.size() > 0)) {

            final List_DeL_Tarefa list_d_ltarefa2 = rela2.get(position);

            holder.txtId.setText(String.valueOf(list_d_ltarefa2.ID_T));
            holder.txtNomeLista.setText(list_d_ltarefa2.LISTATAREFA);
            holder.txtDataLista.setText(list_d_ltarefa2.DATALISTA);

            holder.btnDelete.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = v;
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Confirmação")
                            .setMessage("Deseja excluir esta lista?")
                            .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DataBaseLista reposi = new DataBaseLista(view.getContext());
                                    reposi.excluirLTare(list_d_ltarefa2.getID_T());
                                    removerCliente(list_d_ltarefa2);
                                    Snackbar.make(view, "Excluido", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();

                                }
                            })
                            .setNegativeButton("Cancelar", null)
                            .create()
                            .show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return rela2.size();
    }

    public class ViewHolderListadeLTaref extends RecyclerView.ViewHolder{

        public TextView txtId;
        public TextView txtNomeLista;
        public ImageButton btnEditar;
        public ImageButton btnDelete;
        public TextView txtDataLista;

        public ViewHolderListadeLTaref(View itemView, final Context context) {
            super(itemView);

            txtId = (TextView)itemView.findViewById(R.id.txtId);
            txtNomeLista = (TextView)itemView.findViewById(R.id.txtNomeLista);
            btnEditar = (ImageButton) itemView.findViewById(R.id.btnEditar);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
            txtDataLista = (TextView)itemView.findViewById(R.id.txtDataLista);


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                   // Add_RelacaoList add_relacaoList = new Add_RelacaoList();
                    //add_relacaoList.mHandler.removeCallbacks(add_relacaoList.mToastRunnable);


                    if (rela2.size() > 0 ) {

                        List_DeL_Tarefa list_DeL_Tarefa = rela2.get(getLayoutPosition());

                        Intent it = new Intent(context, List_t.class);
                        it.putExtra("LISTA_DE_LIST_TARE", list_DeL_Tarefa);
                        ((AppCompatActivity) context).startActivityForResult(it, 0);

                    }
                }
            });

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (rela2.size() > 0 ) {

                        List_DeL_Tarefa list_DeL_Tarefa = rela2.get(getLayoutPosition());

                        Intent it = new Intent(context, Add_RelacaoLisTare.class);
                        it.putExtra("LISTA_DE_LIST_TARE", list_DeL_Tarefa);
                        ((AppCompatActivity) context).startActivityForResult(it, 0);

                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if (rela2.size() > 0 ) {

                        List_DeL_Tarefa list_DeL_Tarefa = rela2.get(getLayoutPosition());

                        Intent it = new Intent(context, Add_RelacaoLisTare.class);
                        it.putExtra("LIST_TAREFA_C", list_DeL_Tarefa);
                        ((AppCompatActivity) context).startActivityForResult(it, 0);

                    }
                    return true;
                }
            });


        }
    }
}
