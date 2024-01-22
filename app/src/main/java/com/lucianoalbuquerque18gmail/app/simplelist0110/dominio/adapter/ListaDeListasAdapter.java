package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lucianoalbuquerque18gmail.app.simplelist0110.Add_RelacaoList;
import com.lucianoalbuquerque18gmail.app.simplelist0110.Lista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.R;
import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Lista_De_List;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.ListaDeListReposi;


import java.util.List;

public class ListaDeListasAdapter extends RecyclerView.Adapter<ListaDeListasAdapter.ViewHolderListadeListas> {

  private ListaDeListasAdapter listaDeListasAdapter;
  private DataBaseLista dataBaseLista;
  private SQLiteDatabase conexaoList;
  private List<Lista_De_List> rela;
  private List<Lista_De_List> rela4;
  private Lista_De_List lista_de_list;
  public Context mContext;
  private Dialog myDialog;

  public  ListaDeListasAdapter(List<Lista_De_List> rela){
    this.rela = rela;
  }

  public void atualizarNomeList(Lista_De_List lista_de_list){
    int position = rela.indexOf(lista_de_list);
    this.notifyItemChanged(position);
   // rela.set(rela.indexOf(lista_de_list), lista_de_list);
    //notifyItemChanged(rela.indexOf(lista_de_list));
  }

  public void adicionarCliente(Lista_De_List lista_de_list){
    rela.add(lista_de_list);
    notifyItemInserted(getItemCount());
  }

  public void removerCliente(Lista_De_List lista_de_list){
    int position = rela.indexOf(lista_de_list);
    rela.remove(position);
    notifyItemRemoved(position);
  }

  @Override
  public ListaDeListasAdapter.ViewHolderListadeListas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.linha_relacaolist, parent, false);
    final ViewHolderListadeListas holderListadeListas = new ViewHolderListadeListas(view,parent.getContext());

    return holderListadeListas;
  }

  @Override
  public void onBindViewHolder(@NonNull final ListaDeListasAdapter.ViewHolderListadeListas holder, int position) {

    if ((rela != null) && (rela.size() > 0)) {

      final Lista_De_List lista_De_List2 = rela.get(position);

      holder.txtId.setText(String.valueOf(lista_De_List2.ID));
      holder.txtNomeLista.setText(lista_De_List2.NOMELISTA);
      holder.txtDataLista.setText(lista_De_List2.DATALISTA);

      holder.btnDelete.setOnClickListener(new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
          final View view = v;
          AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
          builder.setTitle(R.string.title_adapterlistas_dellista)
                  .setMessage(R.string.msg_adapterlistas_dellista)
                  .setPositiveButton(R.string.txt_adapterlistas_dellista, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      DataBaseLista reposi = new DataBaseLista(view.getContext());
                      reposi.excluirL(lista_De_List2.getID());
                      removerCliente(lista_De_List2);
                      Snackbar.make(view, R.string.action_adapterlistas_dellista, Snackbar.LENGTH_LONG)
                              .setAction("Action", null).show();

                    }
                  })
                  .setNegativeButton(R.string.txtcancel_adapterlistas_dellista, null)
                  .create()
                  .show();
        }
      });
    }
  }

  @Override
  public int getItemCount() {
    return rela.size();
  }


  public class ViewHolderListadeListas extends RecyclerView.ViewHolder{

    public TextView txtId;
    public TextView txtNomeLista;
    public TextView txtDataLista;
    public ImageButton btnEditar, btnCompartilhar, btnDelete;


    public ViewHolderListadeListas(View itemView, final Context context) {
      super(itemView);

      txtId = (TextView)itemView.findViewById(R.id.txtId);
      txtNomeLista = (TextView)itemView.findViewById(R.id.txtNomeLista);
      txtDataLista = (TextView)itemView.findViewById(R.id.txtDataLista);
      btnEditar = (ImageButton) itemView.findViewById(R.id.btnEditar);
      btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
      btnCompartilhar = (ImageButton) itemView.findViewById(R.id.btnCompartilhar);



      itemView.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {

          if (rela.size() > 0 ) {

            Lista_De_List lista_de_list = rela.get(getLayoutPosition());

            Intent it = new Intent(context, Lista.class);
            it.putExtra("LISTA_DE_LIST", lista_de_list);
            ((AppCompatActivity) context).startActivityForResult(it, 0);

          }
        }
      });

      itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

          if (rela.size() > 0 ) {

            Lista_De_List lista_de_list = rela.get(getLayoutPosition());

            Intent it = new Intent(context, Add_RelacaoList.class);
            it.putExtra("LISTA_DE_LIST_COPIA", lista_de_list);
            ((AppCompatActivity) context).startActivityForResult(it, 0);


          }
          return true;
        }
      });

      btnEditar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

          if (rela.size() > 0 ) {

            Lista_De_List lista_de_list = rela.get(getLayoutPosition());

            Intent it = new Intent(context, Add_RelacaoList.class);
            it.putExtra("LISTA_DE_LIST", lista_de_list);
            ((AppCompatActivity) context).startActivityForResult(it, 0);

          }
        }
      });

      btnCompartilhar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

          if (rela.size() > 0 ) {

            Lista_De_List lista_de_list = rela.get(getLayoutPosition());

            Intent it = new Intent(context, Add_RelacaoList.class);
            it.putExtra("COMPART", lista_de_list);
            ((AppCompatActivity) context).startActivityForResult(it, 0);

          }
        }
      });

      btnCompartilhar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

          if (rela.size() > 0 ) {

            Lista_De_List lista_de_list = rela.get(getLayoutPosition());

            Intent it = new Intent(context, Add_RelacaoList.class);
            it.putExtra("PERMISSAO_COMPART", lista_de_list);
            ((AppCompatActivity) context).startActivityForResult(it, 0);

          }
        }
      });

    }
  }


}
