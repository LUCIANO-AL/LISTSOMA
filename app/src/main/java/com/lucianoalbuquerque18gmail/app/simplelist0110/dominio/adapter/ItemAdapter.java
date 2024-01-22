package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.lucianoalbuquerque18gmail.app.simplelist0110.Lista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.R;
import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Item;

import java.text.DecimalFormat;
import java.util.List;

import static android.app.PendingIntent.getActivity;
import static android.media.CamcorderProfile.get;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.Lista.txtContTodosItens;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.Lista.txtReaisTodosItens;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.Lista.txtTotalReais_NoCheck;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.Lista.txtTotalitem_NoCheck;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.Lista.txtTotalItem_check;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.Lista.txtTotal_check;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.Lista.edtId_Upd;
import static java.lang.Double.valueOf;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolderItem> {

    public List<Item> dados;
    private Context mContext;

    public ItemAdapter(Context mContext, List<Item> dados) {
        this.mContext = mContext;
        this.dados = dados;
    }
   /* public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("my_array_key" , (Serializable) dados);
    }
    public void onRestoreInstanceState(Bundle state) {
        if (state != null) {
            dados = (List<Item>) state.getSerializable("my_array_key");
        }
    }

    */

   /* public void atualizarItem(Item item){
        int position = dados.indexOf(item);
        this.notifyItemChanged(position);
        // rela.set(rela.indexOf(lista_de_list), lista_de_list);
        //notifyItemChanged(rela.indexOf(lista_de_list));
    }*/

    public void adicionarCliente(Item item){
        dados.add(item);
        notifyItemInserted(getItemCount());
    }

    public void refreshListCompras(){
        notifyDataSetChanged();
    }

    public void funcTotalItem_NoCheck(){
        DataBaseLista dataBaseLista = new DataBaseLista(mContext);
        if(dataBaseLista.totalItem_NoCheck(Integer.parseInt(edtId_Upd.getText().toString())) != null){
            txtTotalitem_NoCheck.setText("("+dataBaseLista.totalItem_NoCheck(Integer.parseInt(edtId_Upd.getText().toString()))+")");
        }else{
            txtTotalitem_NoCheck.setText("(0)");
        }

    }
    public void funcTotalReais_NoCheck(){
        DataBaseLista dataBaseLista = new DataBaseLista(mContext);
        if(dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalReais_NoCheck.setText("R$0.00");
        } else{
            txtTotalReais_NoCheck.setText("R$"+dataBaseLista.totalReais_NoChek(Double.parseDouble(edtId_Upd.getText().toString())));
        }
    }
    public void funcTotalItem_check(){
        DataBaseLista dataBaseLista = new DataBaseLista(mContext);
        if(dataBaseLista.totalItem_check(Integer.parseInt(edtId_Upd.getText().toString())) != null){
            txtTotalItem_check.setText("("+dataBaseLista.totalItem_check(Integer.parseInt(edtId_Upd.getText().toString()))+")");
        }else{
            txtTotalItem_check.setText("(0)");
        }

    }
    public void funcTotal_check(){
        DataBaseLista dataBaseLista = new DataBaseLista(mContext);
        if(dataBaseLista.somarCategoria2_check(Integer.parseInt(edtId_Upd.getText().toString())) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotal_check.setText("R$0.00");
        } else{
            txtTotal_check.setText("R$"+dataBaseLista.totalReais_check(Double.parseDouble(edtId_Upd.getText().toString())));
        }
    }
    public void funcTotalItem(){
        DataBaseLista dataBaseLista = new DataBaseLista(mContext);
        if(dataBaseLista.totalItens(Integer.parseInt(edtId_Upd.getText().toString())) != null){
            txtContTodosItens.setText("("+dataBaseLista.totalItens(Integer.parseInt(edtId_Upd.getText().toString()))+")");
        }else{
            txtContTodosItens.setText("(0)");
        }

    }
    public void funcTotalReais(){
        DataBaseLista dataBaseLista = new DataBaseLista(mContext);
        if(dataBaseLista.totalReais(Integer.parseInt(edtId_Upd.getText().toString())) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtReaisTodosItens.setText("R$0.00");
        } else{
            txtReaisTodosItens.setText("R$"+dataBaseLista.totalReais(Double.parseDouble(edtId_Upd.getText().toString())));
        }
    }

    public void funcoesTotaisCheckNoCheck(){
        funcTotalItem_NoCheck();
        funcTotalReais_NoCheck();
        funcTotalItem_check();
        funcTotal_check();
        funcTotalItem();
        funcTotalReais();
        refreshListCompras();
    }


    @Override
    public ItemAdapter.ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.linha_lista, parent, false);
        ViewHolderItem holderItem = new ViewHolderItem(view, parent.getContext());

        return holderItem;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemAdapter.ViewHolderItem holder, final int position) {

        if ((dados != null) && (dados.size() > 0)) {

            final Item itemm = dados.get(position);

            holder.txtIdItem.setText(String.valueOf(itemm.id_item));
            holder.txtNome.setText(itemm.Nome);
            holder.txtPreco.setText(String.valueOf(itemm.preco));
            holder.txtQuant.setText(itemm.quant);
            holder.txtID.setText(String.valueOf(itemm.ID));
            holder.checkBox.setChecked(itemm.check);

            double preco = Double.parseDouble(holder.txtPreco.getText().toString());
            double quant = Double.parseDouble(holder.txtQuant.getText().toString());

            double total = (preco * quant);

            DecimalFormat formato = new DecimalFormat("0.00");

            holder.txtTotal.setText(String.valueOf(formato.format(total)));
           // holder.txtTotal.setText(String.valueOf(total));

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = v;
                    DataBaseLista reposi = new DataBaseLista(view.getContext());
                    if(holder.checkBox.isChecked()){
                        reposi.check(itemm.id_item);
                        holder.txtNome.setPaintFlags( holder.txtNome.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        funcoesTotaisCheckNoCheck();

                    } else{
                        reposi.noCheck(itemm.id_item);
                        holder.txtNome.setPaintFlags(0);

                        funcoesTotaisCheckNoCheck();
                    }
                }
            });

            if (holder.checkBox.isChecked()){
                //holder.checkBox.isChecked();
                holder.txtNome.setPaintFlags( holder.txtNome.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.itemContraintLayout.setBackgroundResource(R.drawable.contornofino_pago_div);
                // holder.ConstraintLayout2.setBackgroundResource(R.drawable.contornofino_b);
                //holder.checkBox.setBackgroundResource(R.drawable.contornofino);
                // holder.checkBox.setBackgroundColor(Color.parseColor("#FFFFFF"));

            } else  if(!holder.checkBox.isChecked()){
                // holder.checkBox.setVisibility(View.INVISIBLE);
                //blah.setVisibility(View.GONE);
                holder.txtNome.setPaintFlags(0);
                holder.itemContraintLayout.setBackgroundResource(R.drawable.contornofino);
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

        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class ViewHolderItem extends ViewHolder{

        public TextView txtIdItem, txtNome, txtPreco, txtQuant, txtID, txtTotal;
        public CheckBox checkBox;
        public ConstraintLayout itemContraintLayout;

        public ViewHolderItem(final View itemView, final Context context) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            txtIdItem = (TextView) itemView.findViewById(R.id.txtIdItem);
            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            txtPreco = (TextView) itemView.findViewById(R.id.txtPreco);
            txtQuant = (TextView) itemView.findViewById(R.id.txtQuant);
            txtID = (TextView) itemView.findViewById(R.id.txtID);
            txtTotal = (TextView) itemView.findViewById(R.id.txtTotal);
            itemContraintLayout = (ConstraintLayout) itemView.findViewById(R.id.itemContraintLayout);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (dados.size() > 0 ) {
                        Item itemm = dados.get(getLayoutPosition());
                        Intent it = new Intent(context, Lista.class);
                        it.putExtra("ITEM_DEL", itemm);
                        ((AppCompatActivity) context).startActivity(it);
                    }
                    return true; }
            });

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (dados.size() > 0 ) {
                        Item itemm = dados.get(getLayoutPosition());
                        Intent it = new Intent(context, Lista.class);
                        it.putExtra("ITEM", itemm);
                        ((AppCompatActivity) context).startActivity(it);

                    }
                }
            });

        }
    }
}
