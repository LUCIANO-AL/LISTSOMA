package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas;
import com.lucianoalbuquerque18gmail.app.simplelist0110.R;
import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Divida;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.Tarefa_Reposi;

import java.util.List;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.checkDivRecTodos;


import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.adapterspinnerNomeCredorPorData;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.adapterspinnerNomeCredorTodos;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.checkSpinnePorCredor;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.checkSpinneTodasPorCredor;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.radioAbertoCredor;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.radioAbertoPorData;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.radioAbertoTodos;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.radioAbertoTodosPorCredor;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.radioPagoCredor;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.radioPagoPorData;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.radioPagoTodos;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.radioPagoTodosPorCredor;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.spinnerNomeCredorTodos;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.spinnerNomeDoCredorPorData;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.txtContAberto;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.txtContPG;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.txtDataFinal;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.txtDataInicial;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.txtTotalAberto;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.txtTotalCont;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.txtTotalPG;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.List_Dividas.txtTotalReais;

public class AdapterDividas extends RecyclerView.Adapter<AdapterDividas.ViewHolderDivida> {

    public List<Divida> dados;
    private boolean checkBox;
    private Context mContext;

    //private static final String CHECKBOX_CHECKED_KEY = "is_checkbox_checked";
    public Divida divida;
    private Tarefa_Reposi tarefa_reposi;

    public AdapterDividas(Context mContext, List<Divida> dados) {
        this.mContext = mContext;
        this.dados = dados;
    }

    public void refreshList(){
        notifyDataSetChanged();
    }

    public void adicionarCliente(Divida divida){
        dados.add(divida);
        notifyItemInserted(getItemCount());
    }

    public void removerCliente(Divida divida){
        int position = dados.indexOf(divida);
        dados.remove(position);
        notifyItemRemoved(position);
    }

    public void func_ContPGPorData(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalPG(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) != null){

            txtContPG.setText("("+dataBase.totalPG(txtDataInicial.getText().toString(), txtDataFinal.getText().toString())+")");
        }else{
            txtContPG.setText("(0)");
        }
    }
    public void func_TotalPGPorData(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarPG(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalPG.setText("R$0.00");
        } else{
            txtTotalPG.setText("R$"+dataBase.somarPG(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
        }
    }
    public void func_ContAbertoPorData(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalAberto(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) != null){

            txtContAberto.setText("("+dataBase.totalAberto(txtDataInicial.getText().toString(), txtDataFinal.getText().toString())+")");
        }else{
            txtContAberto.setText("(0)");
        }

    }
    public void func_TotalAbertoPorData(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarAberto(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalAberto.setText("R$0.00");
        } else{
            txtTotalAberto.setText("R$"+dataBase.somarAberto(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
        }
    }
    public void func_totalContPorData(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalContPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) != null){
            txtTotalCont.setText("("+dataBase.totalContPorDataRec(txtDataInicial.getText().toString(), txtDataFinal.getText().toString())+")");
        }else{
            txtTotalCont.setText("(0)");
        }

    }
    public void func_totalReaisPorData(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalReaisPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalReais.setText("R$0.00");
        } else{
            txtTotalReais.setText("R$"+dataBase.totalReaisPorDataRec(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
        }
    }

    public void funcContPGPorDataCredor(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalPG_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) != null){
            txtContPG.setText("("+dataBase.totalPG_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                    txtDataInicial.getText().toString(), txtDataFinal.getText().toString())+")");
        }else{
            txtContPG.setText("(0)");
        }
    }
    public void funcTotalPGPorDataCredor(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarPG2_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalPG.setText("R$0.00");
        } else{
            txtTotalPG.setText("R$"+dataBase.somarPG2_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                    txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
        }
    }
    public void funcContAbertoPorDataCredor(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalAberto_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) != null){
            txtContAberto.setText("("+dataBase.totalAberto_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                    txtDataInicial.getText().toString(), txtDataFinal.getText().toString())+")");
        }else{
            txtContAberto.setText("(0)");
        }

    }
    public void funcTotalAbertoPorDataCredor(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarAberto2_Filtro(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalAberto.setText("R$0.00");
        } else{
            txtTotalAberto.setText("R$"+dataBase.somarAberto2_Filtro( spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                    txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
        }
    }
    public void func_totalContPorDataCredor(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalContPorDataCredor(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) != null){

            txtTotalCont.setText("("+dataBase.totalContPorDataCredor(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                    txtDataInicial.getText().toString(), txtDataFinal.getText().toString())+")");

        }else{
            txtTotalCont.setText("(0)");
        }

    }
    public void func_totalReaisPorDataCredor(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalReaisPorDataCredor(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                txtDataInicial.getText().toString(), txtDataFinal.getText().toString()) == null){

            txtTotalReais.setText("R$0.00");
        } else{
            txtTotalReais.setText("R$"+dataBase.totalReaisPorDataClienteRec(spinnerNomeDoCredorPorData.getSelectedItem().toString(),
                    txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
        }
    }

    public void func_ContPGTodos(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalPGTodos() != null){
            txtContPG.setText("("+dataBase.totalPGTodos()+")");
        }else{
            txtContPG.setText("(0)");
        }
    }
    public void func_TotalPGTodos(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarPGTodos() == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalPG.setText("R$0.00");
        } else{
            txtTotalPG.setText("R$"+dataBase.somarPGTodos());
        }
    }
    public void func_ContAbertoTodos(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalAbertoTodos() != null){
            txtContAberto.setText("("+dataBase.totalAbertoTodos()+")");
        }else{
            txtContAberto.setText("(0)");
        }
    }
    public void func_TotalAbertoTodos(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarAbertoTodos() == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalAberto.setText("R$0.00");
        } else{
            txtTotalAberto.setText("R$"+dataBase.somarAbertoTodos());
        }
    }
    public void func_totalContTodos(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalContTodosDiv() != null){
            txtTotalCont.setText("("+dataBase.totalContTodosDiv()+")");
        }else{
            txtTotalCont.setText("(0)");
        }

    }
    public void func_totalReaisTodos(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalReaisTodosDiv() == null){

            txtTotalReais.setText("R$0.00");
        } else{
            txtTotalReais.setText("R$"+dataBase.totalReaisTodosDiv());
        }
    }

    public void func_ContPGSpinnerTodosPorCredor(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalPGSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()) != null){
            txtContPG.setText("("+dataBase.totalPGSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString())+")");
        }else{
            txtContPG.setText("(0)");
        }
    }
    public void func_TotalPGSpinnerTodosPorCredor(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarPGSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()) == null){
            txtTotalPG.setText("R$0.00");
        } else{
            txtTotalPG.setText("R$"+dataBase.somarPGSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()));
        }
    }
    public void func_ContAbertoSpinnerTodosPorCredor(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalAbertoSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()) != null){
            txtContAberto.setText("("+dataBase.totalAbertoSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString())+")");
        }else{
            txtContAberto.setText("(0)");
        }

    }
    public void func_TotalAbertoSpinnerTodosPorCredor(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarAbertoSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()) == null){
            txtTotalAberto.setText("R$0.00");
        } else{
            txtTotalAberto.setText("R$"+dataBase.somarAbertoSpinnerTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()));
        }
    }
    public void func_totalContSpinnerTodosPorCredor(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalContTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()) != null){
            txtTotalCont.setText("("+dataBase.totalContTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString())+")");
        }else{
            txtTotalCont.setText("(0)");
        }

    }
    public void func_totalReaisSpinnerTodosPorCredor(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalReaisTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()) == null){
            txtTotalReais.setText("R$0.00");
        } else{
            txtTotalReais.setText("R$"+dataBase.totalReaisTodosPorCredor(spinnerNomeCredorTodos.getSelectedItem().toString()));
        }
    }

    public void filtrosAdpterPorCredor_PorData(){
        funcContPGPorDataCredor();
        funcTotalPGPorDataCredor();
        funcContAbertoPorDataCredor();
        funcTotalAbertoPorDataCredor();
        func_totalContPorDataCredor();
        func_totalReaisPorDataCredor();
        refreshList();

    }

    public void todosAdpterPorData(){
        func_ContPGPorData();
        func_TotalPGPorData();
        func_ContAbertoPorData();
        func_TotalAbertoPorData();
        func_totalContPorData();
        func_totalReaisPorData();
        refreshList();
    }

    public void todasDivAdapter(){
        func_ContPGTodos();
        func_TotalPGTodos();
        func_ContAbertoTodos();
        func_TotalAbertoTodos();
        func_totalContTodos();
        func_totalReaisTodos();
        refreshList();
    }

    public void todasDivPorCredorAdapter(){
        func_ContPGSpinnerTodosPorCredor();
        func_TotalPGSpinnerTodosPorCredor();
        func_ContAbertoSpinnerTodosPorCredor();
        func_TotalAbertoSpinnerTodosPorCredor();
        func_totalContSpinnerTodosPorCredor();
        func_totalReaisSpinnerTodosPorCredor();
        refreshList();
    }

    @Override
    public AdapterDividas.ViewHolderDivida onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.linha_divida, parent, false);
        ViewHolderDivida holderDivida = new ViewHolderDivida(view, parent.getContext());

        return holderDivida;

    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterDividas.ViewHolderDivida holder, final int position) {

        if ((dados != null) && (dados.size() > 0)) {

            final Divida divida = dados.get(position);
            // holder.checkBox.setOnCheckedChangeListener (null);
            holder.txtDivida.setText(divida.NOMEDIV);
            holder.txtCredor.setText(divida.NOMEDOCREDOR);
            holder.txtValor.setText(divida.VALORDIV);
            holder.txtDataInicial.setText(divida.DATADIV);
            holder.txtVencimento.setText(divida.DATAVENC);
            holder.txtID.setText(String.valueOf(divida.ID_LDIV));
            holder.checkBox.setChecked(divida.PG);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(View v) {

                    final View view = v;
                    final DataBaseLista reposi = new DataBaseLista(view.getContext());

                    if(holder.checkBox.isChecked()) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Confirmação")
                                .setMessage("Registrar pagamento?")
                                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if(checkSpinnePorCredor.isChecked()){
                                            reposi.DivPaga(divida.ID);
                                            holder.txtDivida.setPaintFlags(holder.txtDivida.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor.setPaintFlags(holder.txtValor.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto.setVisibility(View.GONE);
                                            holder.txtPago.setVisibility(View.VISIBLE);

                                            filtrosAdpterPorCredor_PorData();
                                            radioAbertoCredor.performClick();

                                        } else if(checkDivRecTodos.isChecked()) {
                                            reposi.DivPaga(divida.ID);
                                            holder.txtDivida.setPaintFlags(holder.txtDivida.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor.setPaintFlags(holder.txtValor.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto.setVisibility(View.GONE);
                                            holder.txtPago.setVisibility(View.VISIBLE);

                                            todasDivAdapter();
                                            radioAbertoTodos.performClick();

                                        } else if(checkSpinneTodasPorCredor.isChecked()) {
                                            reposi.DivPaga(divida.ID);
                                            holder.txtDivida.setPaintFlags(holder.txtDivida.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor.setPaintFlags(holder.txtValor.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto.setVisibility(View.GONE);
                                            holder.txtPago.setVisibility(View.VISIBLE);

                                            todasDivPorCredorAdapter();
                                            radioAbertoTodosPorCredor.performClick();

                                        } else {
                                            reposi.DivPaga(divida.ID);
                                            holder.txtDivida.setPaintFlags(holder.txtDivida.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor.setPaintFlags(holder.txtValor.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto.setVisibility(View.GONE);
                                            holder.txtPago.setVisibility(View.VISIBLE);

                                            todosAdpterPorData();
                                            radioAbertoPorData.performClick();
                                        }

                                    }
                                })

                                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        holder.checkBox.setChecked(false);
                                    }
                                })
                                 //.setNegativeButton("Cancelar", null)
                                .create()
                                .show();
                                                // holder.ConstraintLayout2.setBackgroundResource(R.drawable.contornofino_b);
                        // holder.checkBox.setBackgroundResource(R.drawable.contornofino);
                    } else{

                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Confirmação")
                                .setMessage("Desfazer registro de pagamento?")
                                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        boolean checkfiltro = checkSpinnePorCredor.isChecked();
                                        boolean checktodos = checkDivRecTodos.isChecked();

                                        if(List_Dividas.checkSpinnePorCredor.isChecked()){
                                            reposi.DivNoPaga(divida.ID);
                                            holder.txtDivida.setPaintFlags(holder.txtDivida.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor.setPaintFlags(holder.txtValor.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto.setVisibility(View.GONE);
                                            holder.txtPago.setVisibility(View.VISIBLE);

                                            filtrosAdpterPorCredor_PorData();
                                            radioPagoCredor.performClick();

                                        } else if(checkDivRecTodos.isChecked()) {

                                            reposi.DivNoPaga(divida.ID);
                                            holder.txtDivida.setPaintFlags(holder.txtDivida.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor.setPaintFlags(holder.txtValor.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto.setVisibility(View.GONE);
                                            holder.txtPago.setVisibility(View.VISIBLE);

                                            todasDivAdapter();
                                            radioPagoTodos.performClick();

                                        }  else if(checkSpinneTodasPorCredor.isChecked()) {

                                            reposi.DivNoPaga(divida.ID);
                                            holder.txtDivida.setPaintFlags(holder.txtDivida.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor.setPaintFlags(holder.txtValor.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto.setVisibility(View.GONE);
                                            holder.txtPago.setVisibility(View.VISIBLE);

                                            todasDivPorCredorAdapter();
                                            radioPagoTodosPorCredor.performClick();

                                        }else {

                                            reposi.DivNoPaga(divida.ID);
                                            holder.txtDivida.setPaintFlags(holder.txtDivida.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor.setPaintFlags(holder.txtValor.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto.setVisibility(View.GONE);
                                            holder.txtPago.setVisibility(View.VISIBLE);

                                            todosAdpterPorData();
                                            radioPagoPorData.performClick();

                                        }

                                    }
                                })
                                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        holder.checkBox.setChecked(true);
                                    }
                                })
                                //.setNegativeButton("Cancelar", null)
                                .create()
                                .show();

                        //holder.ConstraintLayout2.setBackgroundResource(R.drawable.contornofino);
                        // holder.checkBox.setBackgroundResource(R.color.browser_actions_bg_grey);

                    }
                }
            });


            if (holder.checkBox.isChecked()){
                //holder.checkBox.isChecked();
                holder.txtDivida.setPaintFlags( holder.txtDivida.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.txtAberto.setVisibility(View.GONE);
                holder.txtPago.setVisibility(View.VISIBLE);
                holder.dividaContraintLayout.setBackgroundResource(R.drawable.contornofino_pago_div);
                //holder.checkBox.setBackgroundResource(R.drawable.contornofino);
                // holder.checkBox.setBackgroundColor(Color.parseColor("#FFFFFF"));

            } else {
                // holder.checkBox.setVisibility(View.INVISIBLE);
                //blah.setVisibility(View.GONE);
                holder.txtDivida.setPaintFlags(0);
                holder.txtValor.setPaintFlags(0);
                holder.txtAberto.setVisibility(View.VISIBLE);
                holder.txtPago.setVisibility(View.GONE);
                holder.dividaContraintLayout.setBackgroundResource(R.drawable.contornofino);
                //holder.checkBox.setBackgroundResource(R.color.browser_actions_bg_grey);

            }

            holder.checkBox.setChecked (dados.get (position).getChecked());

            holder.checkBox.setOnCheckedChangeListener ( new CompoundButton.OnCheckedChangeListener () {
                @Override
                public void onCheckedChanged (CompoundButton buttonView, boolean PG) {

                    dados.get (holder.getAdapterPosition ()). setChecked (PG);
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
                                    reposi.excluirDivida(divida.ID);
                                    removerCliente(divida);

                                    if(List_Dividas.checkSpinnePorCredor.isChecked()){

                                        adapterspinnerNomeCredorPorData = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
                                                reposi.buscarNomeCredorSppinerPorData(txtDataInicial.getText().toString(), txtDataFinal.getText().toString()));
                                        spinnerNomeDoCredorPorData.setAdapter(adapterspinnerNomeCredorPorData);

                                        filtrosAdpterPorCredor_PorData();


                                    } else if(checkDivRecTodos.isChecked()){

                                        adapterspinnerNomeCredorTodos = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
                                                reposi.buscarNomeCredorSppinerTodos());
                                        spinnerNomeDoCredorPorData.setAdapter(adapterspinnerNomeCredorTodos);

                                        todasDivAdapter();


                                    } else if(checkSpinneTodasPorCredor.isChecked()){

                                        adapterspinnerNomeCredorTodos = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
                                                reposi.buscarNomeCredorSppinerTodos());
                                        spinnerNomeDoCredorPorData.setAdapter(adapterspinnerNomeCredorTodos);

                                        todasDivPorCredorAdapter();


                                    } else {
                                        todosAdpterPorData();
                                    }

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

    public class ViewHolderDivida extends RecyclerView.ViewHolder {

        public TextView txtDivida;
        public TextView txtCredor;
        public TextView txtValor;
        public TextView txtDataInicial;
        public TextView txtVencimento;
        public TextView txtAberto;
        public TextView txtPago;
        public TextView txtID;

        public ImageButton btnDelete;

        public CheckBox checkBox;

       public ConstraintLayout dividaContraintLayout;

        public ViewHolderDivida(final View itemView, final Context context) {
            super(itemView);

            txtDivida = (TextView) itemView.findViewById(R.id.txtDivida);
            txtCredor = (TextView) itemView.findViewById(R.id.txtCredor);
            txtValor = (TextView) itemView.findViewById(R.id.txtValor);
            txtDataInicial = (TextView) itemView.findViewById(R.id.txtDataInicial);
            txtVencimento = (TextView) itemView.findViewById(R.id.txtVencimento);
            txtAberto = (TextView) itemView.findViewById(R.id.txtAberto);
            txtPago = (TextView) itemView.findViewById(R.id.txtPago);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            txtID = (TextView) itemView.findViewById(R.id.txtID);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
            dividaContraintLayout = (ConstraintLayout) itemView.findViewById(R.id.dividaContraintLayout);

  /*   checkBox.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {

          if (dados.size() > 0 ) {

            Divida divida = dados.get(getLayoutPosition());

            List_Dividas list_dividas = new List_Dividas();



              context.funcContPG();
              list_dividas.funcTotalPG();
              list_dividas.funcContAberto();
              list_dividas.funcTotalAberto();

          }
        }
      });

   */

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (dados.size() > 0 ) {

                        Divida divida = dados.get(getLayoutPosition());

                        Intent it = new Intent(context, List_Dividas.class);
                        it.putExtra("ITEM_DIV", divida);
                        ((AppCompatActivity) context).startActivityForResult(it,0);

                    }
                }
            });

        }
    }
}
