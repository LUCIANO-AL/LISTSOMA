package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.lucianoalbuquerque18gmail.app.simplelist0110.List_t;
import com.lucianoalbuquerque18gmail.app.simplelist0110.R;
import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Tarefa;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.Tarefa_Reposi;

import java.util.List;

public class Tarefa_Adapter extends RecyclerView.Adapter<Tarefa_Adapter.ViewHolderItem> {

  public List<Tarefa> dados;
  private boolean checkBox;
  private SQLiteDatabase conexao;

  //private static final String CHECKBOX_CHECKED_KEY = "is_checkbox_checked";
  public Tarefa tarefa;
  private Tarefa_Reposi tarefa_reposi;

  public Tarefa_Adapter(List<Tarefa> dados) {

    this.dados = dados;
  }

  public void refreshList(){
    notifyDataSetChanged();
  }

  public void adicionarCliente(Tarefa tarefa){
    dados.add(tarefa);
    notifyItemInserted(getItemCount());
  }

  public void removerCliente(Tarefa tarefa){
    int position = dados.indexOf(tarefa);
    dados.remove(position);
    notifyItemRemoved(position);
  }

  @Override
  public Tarefa_Adapter.ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.tarefa_linha, parent, false);
    ViewHolderItem holderItem = new ViewHolderItem(view, parent.getContext());

    return holderItem;
  }

  @Override
  public void onBindViewHolder(@NonNull final Tarefa_Adapter.ViewHolderItem holder, final int position) {

    if ((dados != null) && (dados.size() > 0)) {

      final Tarefa tare = dados.get(position);

      // holder.checkBox.setOnCheckedChangeListener (null);

      holder.txtTarefa.setText(tare.Tarefa);
      holder.txtPriori.setText(tare.Prioridade);
      holder.txtID.setText(String.valueOf(tare.ID_T));
      holder.checkBox.setChecked(tare.check);

      holder.checkBox.setOnClickListener(new View.OnClickListener() {
        @SuppressLint("NewApi")
        @Override
        public void onClick(View v) {
          final View view = v;
          DataBaseLista reposi = new DataBaseLista(view.getContext());
          if(holder.checkBox.isChecked()){
            reposi.checkT(tare.id_tarefa);
            holder.txtTarefa.setPaintFlags( holder.txtTarefa.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txtPriori.setVisibility(View.GONE);
            holder.txtFeito.setVisibility(View.VISIBLE);
           // holder.ConstraintLayout2.setBackgroundResource(R.drawable.contornofino_b);
           // holder.checkBox.setBackgroundResource(R.drawable.contornofino);
          } else{
            reposi.noCheckT(tare.id_tarefa);
            holder.txtTarefa.setPaintFlags(0);
            holder.txtPriori.setVisibility(View.VISIBLE);
            holder.txtFeito.setVisibility(View.GONE);
            //holder.ConstraintLayout2.setBackgroundResource(R.drawable.contornofino);
           // holder.checkBox.setBackgroundResource(R.color.browser_actions_bg_grey);

          }
        }
      });


      if (holder.checkBox.isChecked()){
        //holder.checkBox.isChecked();
        holder.txtTarefa.setPaintFlags( holder.txtTarefa.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txtPriori.setVisibility(View.GONE);
        holder.txtFeito.setVisibility(View.VISIBLE);
        // holder.ConstraintLayout2.setBackgroundResource(R.drawable.contornofino_b);
        //holder.checkBox.setBackgroundResource(R.drawable.contornofino);
       // holder.checkBox.setBackgroundColor(Color.parseColor("#FFFFFF"));
      } else {
        // holder.checkBox.setVisibility(View.INVISIBLE);
        //blah.setVisibility(View.GONE);
        holder.txtTarefa.setPaintFlags(0);
        holder.txtPriori.setVisibility(View.VISIBLE);
        holder.txtFeito.setVisibility(View.GONE);
       // holder.ConstraintLayout2.setBackgroundResource(R.drawable.contornofino);
        //holder.checkBox.setBackgroundResource(R.color.browser_actions_bg_grey);
      }

      holder.checkBox.setChecked (dados.get (position).getChecked());

      holder.checkBox.setOnCheckedChangeListener ( new CompoundButton.OnCheckedChangeListener () {
        @Override
        public void onCheckedChanged (CompoundButton buttonView, boolean check) {

          dados.get (holder.getAdapterPosition ()). setChecked (check);
        }
      });


      holder.btnDelete.setOnClickListener(new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
          final View view = v;
          AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
          builder.setTitle("Confirmação")
                  .setMessage("Deseja excluir o item?")
                  .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      DataBaseLista reposi = new DataBaseLista(view.getContext());
                      reposi.excluirItTare(tare.id_tarefa);
                      removerCliente(tare);
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
    return dados.size();

  }

  public class ViewHolderItem extends RecyclerView.ViewHolder {

    public TextView txtTarefa;
    public TextView txtPriori;
    public TextView txtFeito;
    public ImageButton btnDelete;
    public CheckBox checkBox;
    public TextView txtID;
    public ConstraintLayout ConstraintLayout2;



    public ViewHolderItem(final View itemView, final Context context) {
      super(itemView);

      txtTarefa = (TextView) itemView.findViewById(R.id.txtTarefa);
      txtPriori = (TextView) itemView.findViewById(R.id.txtPriori);
      txtFeito = (TextView) itemView.findViewById(R.id.feito);
      checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
      btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
      txtID = (TextView) itemView.findViewById(R.id.txtID);
      ConstraintLayout2 = (ConstraintLayout) itemView.findViewById(R.id.constraintLayout2);

    /* checkBox.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {

          if (dados.size() > 0 ) {

            Tarefa tarefa = dados.get(getLayoutPosition());

            Intent it = new Intent(context, List_t.class);
            it.putExtra("ITEMCHECK", tarefa);
            ((AppCompatActivity) context).startActivityForResult(it,0);
          }
        }
      });*/

          itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

              if (dados.size() > 0 ) {

                Tarefa tarefa = dados.get(getLayoutPosition());

                Intent it = new Intent(context, List_t.class);
                it.putExtra("ITEMt", tarefa);
                ((AppCompatActivity) context).startActivityForResult(it,0);

                  }
              }
          });

       }
  }
}
