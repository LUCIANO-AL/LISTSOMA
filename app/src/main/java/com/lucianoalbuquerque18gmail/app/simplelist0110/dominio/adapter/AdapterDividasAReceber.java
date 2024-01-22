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
import com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber;
import com.lucianoalbuquerque18gmail.app.simplelist0110.R;
import com.lucianoalbuquerque18gmail.app.simplelist0110.database.DataBaseLista;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.entidades.Receber;
import com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.repositorio.Tarefa_Reposi;

import java.util.List;

import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.adapterspinnerNomeClientePorData;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.adapterspinnerNomeclienteTodasDivRec;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.checkDivRecTodos;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.checkSpinneNomeClientePorData;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.checkSpinneTodasPorCliente;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.radioAbertoCliente;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.radioAbertoPorData;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.radioAbertoTodos;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.radioAbertoTodosPorCliente;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.radioPagoCliente;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.radioPagoPorData;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.radioPagoTodos;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.radioPagoTodosPorCliente;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.spinnerNomeClientePorData;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.spinnerNomeClienteTodos;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.txtContAberto_Rec;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.txtContPG_Rec;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.txtDataFinal_Rec;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.txtDataInicial_Rec;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.txtTotalAberto_Rec;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.txtTotalContRec;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.txtTotalPG_Rec;
import static com.lucianoalbuquerque18gmail.app.simplelist0110.DividasAReceber.txtTotalReaisRec;


public class AdapterDividasAReceber extends RecyclerView.Adapter<AdapterDividasAReceber.ViewHolderDividaAReceber> {

    public List<Receber> dados;
    private boolean checkBox;
    private Context mContext;

    //private static final String CHECKBOX_CHECKED_KEY = "is_checkbox_checked";
    public Receber receber;
    private Tarefa_Reposi tarefa_reposi;

    public AdapterDividasAReceber(Context mContext, List<Receber> dados) {
        this.mContext = mContext;
        this.dados = dados;
    }

    public DataBaseLista dataBase = new DataBaseLista(mContext);


    public void refreshList(){
        notifyDataSetChanged();
    }

    public void adicionarCliente(Receber receber){
        dados.add(receber);
        notifyItemInserted(getItemCount());
    }

    public void removerCliente(Receber receber){
        int position = dados.indexOf(receber);
        dados.remove(position);
        notifyItemRemoved(position);
    }

    public void func_ContPGPorData(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalPG_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) != null){

            txtContPG_Rec.setText("("+dataBase.totalPG_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString())+")");
        }else{
            txtContPG_Rec.setText("(0)");
        }
    }
    public void func_TotalPGPorData(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarPG_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) == null){
            txtTotalPG_Rec.setText("R$0.00");
        } else{
            txtTotalPG_Rec.setText("R$"+dataBase.somarPG_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
        }
    }
    public void func_ContAbertoPorData(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalAberto_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) != null){

            txtContAberto_Rec.setText("("+dataBase.totalAberto_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString())+")");
        }else{
            txtContAberto_Rec.setText("(0)");
        }

    }
    public void func_TotalAbertoPorData(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarAberto_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) == null){
            txtTotalAberto_Rec.setText("R$0.00");
        } else{
            txtTotalAberto_Rec.setText("R$"+dataBase.somarAberto_Rec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
        }
    }
    public void func_totalContPorData(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalContPorDataRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) != null){
            txtTotalContRec.setText("("+dataBase.totalContPorDataRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString())+")");
        }else{
            txtTotalContRec.setText("(0)");
        }

    }
    public void func_totalReaisPorData(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalReaisPorDataRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalReaisRec.setText("R$0.00");
        } else{
            txtTotalReaisRec.setText("R$"+dataBase.totalReaisPorDataRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
        }
    }

    public void func_ContPGPorDataCliente(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalPG_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) != null){
            txtContPG_Rec.setText("("+dataBase.totalPG_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                    txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString())+")");
        }else{
            txtContPG_Rec.setText("(0)");
        }
    }
    public void func_TotalPGPorDataCliente(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarPG2_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) == null){

            txtTotalPG_Rec.setText("R$0.00");
        } else{
            txtTotalPG_Rec.setText("R$"+dataBase.somarPG2_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                    txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
        }
    }
    public void func_ContAbertoPorDataCliente(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalAberto_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) != null){
            txtContAberto_Rec.setText("("+dataBase.totalAberto_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                    txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString())+")");
        }else{
            txtContAberto_Rec.setText("(0)");
        }

    }
    public void func_TotalAbertoPorDataCliente(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarAberto2_Filtro_Rec(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) == null){

            txtTotalAberto_Rec.setText("R$0.00");
        } else{
            txtTotalAberto_Rec.setText("R$"+dataBase.somarAberto2_Filtro_Rec( spinnerNomeClientePorData.getSelectedItem().toString(),
                    txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
        }
    }
    public void func_totalContPorDataCliente(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalContPorDataClienteRec(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) != null){

            txtTotalContRec.setText("("+dataBase.totalContPorDataClienteRec(spinnerNomeClientePorData.getSelectedItem().toString(),
                    txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString())+")");

        }else{
            txtTotalContRec.setText("(0)");
        }

    }
    public void func_totalReaisPorDataCliente(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalReaisPorDataClienteRec(spinnerNomeClientePorData.getSelectedItem().toString(),
                txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()) == null){

            txtTotalReaisRec.setText("R$0.00");
        } else{
            txtTotalReaisRec.setText("R$"+dataBase.totalReaisPorDataClienteRec(spinnerNomeClientePorData.getSelectedItem().toString(),
                    txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
        }
    }

    public void func_ContPGTodos(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalPG_RecTodos() != null){
            txtContPG_Rec.setText("("+dataBase.totalPG_RecTodos()+")");
        }else{
            txtContPG_Rec.setText("(0)");
        }
    }
    public void func_TotalPGTodos(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarPG_RecTodos() == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalPG_Rec.setText("R$0.00");
        } else{
            txtTotalPG_Rec.setText("R$"+dataBase.somarPG_RecTodos());
        }
    }
    public void func_ContAbertoTodos(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalAberto_RecTodos() != null){
            txtContAberto_Rec.setText("("+dataBase.totalAberto_RecTodos()+")");
        }else{
            txtContAberto_Rec.setText("(0)");
        }

    }
    public void func_TotalAbertoTodos(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarAberto_RecTodos() == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalAberto_Rec.setText("R$0.00");
        } else{
            txtTotalAberto_Rec.setText("R$"+dataBase.somarAberto_RecTodos());
        }
    }
    public void func_totalContTodos(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalContTodosDivRec() != null){
            txtTotalContRec.setText("("+dataBase.totalContTodosDivRec()+")");
        }else{
            txtTotalContRec.setText("(0)");
        }

    }
    public void func_totalReaisTodos(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalReaisTodosDivRec() == null){
            //  txtTotal.setText("R$ "+dataBaseLista.somarCategoria2(Integer.parseInt(edtId_Upd.getText().toString())));
            txtTotalReaisRec.setText("R$0.00");
        } else{
            txtTotalReaisRec.setText("R$"+dataBase.totalReaisTodosDivRec());
        }
    }

    public void func_ContPGSpinnerTodosPorCliente(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalPGSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString()) != null){

            txtContPG_Rec.setText("("+dataBase.totalPGSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString())+")");

        }else{
            txtContPG_Rec.setText("(0)");
        }
    }
    public void func_TotalPGSpinnerTodosPorCliente(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarPGSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString()) == null){

            txtTotalPG_Rec.setText("R$0.00");
        } else{
            txtTotalPG_Rec.setText("R$"+dataBase.somarPGSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString()));
        }
    }
    public void func_ContAbertoSpinnerTodosPorCliente(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalAbertoSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString()) != null){
            txtContAberto_Rec.setText("("+dataBase.totalAbertoSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString())+")");
        }else{
            txtContAberto_Rec.setText("(0)");
        }

    }
    public void func_TotalAbertoSpinnerTodosPorCliente(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.somarAbertoSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString()) == null){
            txtTotalAberto_Rec.setText("R$0.00");
        } else{
            txtTotalAberto_Rec.setText("R$"+dataBase.somarAbertoSpinnerTodosPorCliente(spinnerNomeClienteTodos.getSelectedItem().toString()));
        }
    }
    public void func_totalContSpinnerTodosPorCliente(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalContTodosPorClienteRec(spinnerNomeClienteTodos.getSelectedItem().toString()) != null){
            txtTotalContRec.setText("("+dataBase.totalContTodosPorClienteRec(spinnerNomeClienteTodos.getSelectedItem().toString())+")");
        }else{
            txtTotalContRec.setText("(0)");
        }

    }
    public void func_totalReaisSpinnerTodosPorCliente(){
        DataBaseLista dataBase = new DataBaseLista(mContext);
        if(dataBase.totalReaisTodosPorClienteRec(spinnerNomeClienteTodos.getSelectedItem().toString()) == null){
            txtTotalReaisRec.setText("R$0.00");
        } else{
            txtTotalReaisRec.setText("R$"+dataBase.totalReaisTodosPorClienteRec(spinnerNomeClienteTodos.getSelectedItem().toString()));
        }
    }

    public void buscarClientePorData(){
        func_ContPGPorDataCliente();
        func_TotalPGPorDataCliente();
        func_ContAbertoPorDataCliente();
        func_TotalAbertoPorDataCliente();
        func_totalContPorDataCliente();
        func_totalReaisPorDataCliente();
        refreshList();

    }

    public void buscarPorData(){
        func_ContPGPorData();
        func_TotalPGPorData();
        func_ContAbertoPorData();
        func_TotalAbertoPorData();
        func_totalContPorData();
        func_totalReaisPorData();
        refreshList();
    }

    public void buscarTodasDividasRec(){
        func_ContPGTodos();
        func_TotalPGTodos();
        func_ContAbertoTodos();
        func_TotalAbertoTodos();
        func_totalContTodos();
        func_totalReaisTodos();
        refreshList();
    }

    public void buscarTodasDividasRecPorCliente(){
        func_ContPGSpinnerTodosPorCliente();
        func_TotalPGSpinnerTodosPorCliente();
        func_ContAbertoSpinnerTodosPorCliente();
        func_TotalAbertoSpinnerTodosPorCliente();
        func_totalContSpinnerTodosPorCliente();
        func_totalReaisSpinnerTodosPorCliente();
        refreshList();
    }

    @Override
    public AdapterDividasAReceber.ViewHolderDividaAReceber onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.linha_divida_a_receber, parent, false);
        AdapterDividasAReceber.ViewHolderDividaAReceber holderDivida = new AdapterDividasAReceber.ViewHolderDividaAReceber(view, parent.getContext());

        return holderDivida;

    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterDividasAReceber.ViewHolderDividaAReceber holder, final int position) {

        if ((dados != null) && (dados.size() > 0)) {

            final Receber receber = dados.get(position);

            holder.txtDivida_Rec.setText(receber.NOMEDIV_REC);
            holder.txtCliente.setText(receber.NOMECLIENTE);
            holder.txtValor_Rec.setText(receber.VALORDIV_REC);
            holder.txtDataInicial_Rec.setText(receber.DATADIV_REC);
            holder.txtVencimento_Rec.setText(receber.DATAVENC_REC);
            holder.checkBox_Rec.setChecked(receber.PG_REC);

            holder.checkBox_Rec.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(View v) {

                    final View view = v;
                    final DataBaseLista reposi = new DataBaseLista(view.getContext());

                    if(holder.checkBox_Rec.isChecked()) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Confirmação")
                                .setMessage("Registrar pagamento?")
                                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if(checkSpinneNomeClientePorData.isChecked()){
                                            reposi.DivPaga_Rec(receber.ID);
                                            holder.txtDivida_Rec.setPaintFlags(holder.txtDivida_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor_Rec.setPaintFlags(holder.txtValor_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto_Rec.setVisibility(View.GONE);
                                            holder.txtPago_Rec.setVisibility(View.VISIBLE);

                                            buscarClientePorData();
                                            radioAbertoCliente.performClick();

                                        } else if(checkDivRecTodos.isChecked()){
                                            reposi.DivPaga_Rec(receber.ID);
                                            holder.txtDivida_Rec.setPaintFlags(holder.txtDivida_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor_Rec.setPaintFlags(holder.txtValor_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto_Rec.setVisibility(View.GONE);
                                            holder.txtPago_Rec.setVisibility(View.VISIBLE);

                                            buscarTodasDividasRec();
                                            radioAbertoTodos.performClick();

                                        } else if(checkSpinneTodasPorCliente.isChecked()){

                                            reposi.DivPaga_Rec(receber.ID);
                                            holder.txtDivida_Rec.setPaintFlags(holder.txtDivida_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor_Rec.setPaintFlags(holder.txtValor_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto_Rec.setVisibility(View.GONE);
                                            holder.txtPago_Rec.setVisibility(View.VISIBLE);

                                            buscarTodasDividasRecPorCliente();
                                            radioAbertoTodosPorCliente.performClick();

                                        } else {
                                            reposi.DivPaga_Rec(receber.ID);
                                            holder.txtDivida_Rec.setPaintFlags(holder.txtDivida_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor_Rec.setPaintFlags(holder.txtValor_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto_Rec.setVisibility(View.GONE);
                                            holder.txtPago_Rec.setVisibility(View.VISIBLE);

                                            buscarPorData();
                                            radioAbertoPorData.performClick();
                                        }

                                    }
                                })

                                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        holder.checkBox_Rec.setChecked(false);
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

                                        boolean checkfiltro = checkSpinneNomeClientePorData.isChecked();
                                        boolean checktodos = checkDivRecTodos.isChecked();

                                        if(checkSpinneNomeClientePorData.isChecked()){
                                            reposi.DivNoPaga_Rec(receber.ID);
                                            holder.txtDivida_Rec.setPaintFlags(holder.txtDivida_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor_Rec.setPaintFlags(holder.txtValor_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto_Rec.setVisibility(View.GONE);
                                            holder.txtPago_Rec.setVisibility(View.VISIBLE);

                                            buscarClientePorData();
                                            radioPagoCliente.performClick();

                                        } else if(checkDivRecTodos.isChecked()){
                                            reposi.DivNoPaga_Rec(receber.ID);
                                            holder.txtDivida_Rec.setPaintFlags(holder.txtDivida_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor_Rec.setPaintFlags(holder.txtValor_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto_Rec.setVisibility(View.GONE);
                                            holder.txtPago_Rec.setVisibility(View.VISIBLE);

                                            buscarTodasDividasRec();
                                            radioPagoTodos.performClick();

                                        } else if(checkSpinneTodasPorCliente.isChecked()) {
                                            reposi.DivNoPaga_Rec(receber.ID);
                                            holder.txtDivida_Rec.setPaintFlags(holder.txtDivida_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor_Rec.setPaintFlags(holder.txtValor_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto_Rec.setVisibility(View.GONE);
                                            holder.txtPago_Rec.setVisibility(View.VISIBLE);

                                            buscarTodasDividasRecPorCliente();
                                            radioPagoTodosPorCliente.performClick();

                                        }else {
                                            reposi.DivNoPaga_Rec(receber.ID);
                                            holder.txtDivida_Rec.setPaintFlags(holder.txtDivida_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtValor_Rec.setPaintFlags(holder.txtValor_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            holder.txtAberto_Rec.setVisibility(View.GONE);
                                            holder.txtPago_Rec.setVisibility(View.VISIBLE);

                                            buscarPorData();
                                            radioPagoPorData.performClick();

                                        }

                                    }
                                })
                                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        holder.checkBox_Rec.setChecked(true);
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


            if (holder.checkBox_Rec.isChecked()){
                //holder.checkBox.isChecked();
                holder.txtDivida_Rec.setPaintFlags( holder.txtDivida_Rec.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.txtAberto_Rec.setVisibility(View.GONE);
                holder.txtPago_Rec.setVisibility(View.VISIBLE);
                holder.dividaContraintLayout.setBackgroundResource(R.drawable.contornofino_pago_div);
                //holder.checkBox.setBackgroundResource(R.drawable.contornofino);
                // holder.checkBox.setBackgroundColor(Color.parseColor("#FFFFFF"));

            } else {
                // holder.checkBox.setVisibility(View.INVISIBLE);
                //blah.setVisibility(View.GONE);
                holder.txtDivida_Rec.setPaintFlags(0);
                holder.txtValor_Rec.setPaintFlags(0);
                holder.txtAberto_Rec.setVisibility(View.VISIBLE);
                holder.txtPago_Rec.setVisibility(View.GONE);
                holder.dividaContraintLayout.setBackgroundResource(R.drawable.contornofino);
                //holder.checkBox.setBackgroundResource(R.color.browser_actions_bg_grey);

            }

            holder.checkBox_Rec.setChecked (dados.get (position).getChecked());

            holder.checkBox_Rec.setOnCheckedChangeListener ( new CompoundButton.OnCheckedChangeListener () {
                @Override
                public void onCheckedChanged (CompoundButton buttonView, boolean PG_REC) {

                    dados.get (holder.getAdapterPosition ()). setChecked (PG_REC);
                }
            });


            holder.btnDelete_Rec.setOnClickListener(new Button.OnClickListener() {
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
                                    reposi.excluirDivida_Rec(receber.ID);
                                    removerCliente(receber);

                                    if(checkSpinneNomeClientePorData.isChecked()){
                                        adapterspinnerNomeClientePorData = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
                                                reposi.buscarNomeClienteSppinnerPordaRec(txtDataInicial_Rec.getText().toString(), txtDataFinal_Rec.getText().toString()));
                                        spinnerNomeClientePorData.setAdapter(adapterspinnerNomeClientePorData);

                                        buscarClientePorData();

                                    } else if(checkSpinneTodasPorCliente.isChecked()){

                                        adapterspinnerNomeclienteTodasDivRec = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
                                                reposi.buscarNomeClienteSppinerTodosRec());
                                        spinnerNomeClientePorData.setAdapter(adapterspinnerNomeclienteTodasDivRec);

                                        buscarTodasDividasRecPorCliente();

                                    } else if(checkDivRecTodos.isChecked()){

                                        buscarTodasDividasRec();

                                    }else {
                                        buscarPorData();
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

    public class ViewHolderDividaAReceber extends RecyclerView.ViewHolder {

        public TextView txtDivida_Rec;
        public TextView txtCliente;
        public TextView txtValor_Rec;
        public TextView txtDataInicial_Rec;
        public TextView txtVencimento_Rec;
        public TextView txtAberto_Rec;
        public TextView txtPago_Rec;
        public ImageButton btnDelete_Rec;
        public CheckBox checkBox_Rec;
        public TextView txtID;
        public ConstraintLayout dividaContraintLayout;

        public ViewHolderDividaAReceber(final View itemView, final Context context) {
            super(itemView);

            txtDivida_Rec = (TextView) itemView.findViewById(R.id.txtDivida_Rec);
            txtCliente = (TextView) itemView.findViewById(R.id.txtCliente);
            txtValor_Rec = (TextView) itemView.findViewById(R.id.txtValor_Rec);
            txtDataInicial_Rec = (TextView) itemView.findViewById(R.id.txtDataInicial_Rec);
            txtVencimento_Rec = (TextView) itemView.findViewById(R.id.txtVencimento_Rec);
            txtAberto_Rec = (TextView) itemView.findViewById(R.id.txtAberto_Rec);
            txtPago_Rec = (TextView) itemView.findViewById(R.id.txtPago_Rec);
            checkBox_Rec = (CheckBox) itemView.findViewById(R.id.checkBox_Rec);
            txtID = (TextView) itemView.findViewById(R.id.txtID);
            btnDelete_Rec = (ImageButton) itemView.findViewById(R.id.btnDelete_Rec);
            dividaContraintLayout = (ConstraintLayout) itemView.findViewById(R.id.dividaContraintLayout_A_Receber);


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (dados.size() > 0 ) {

                        Receber receber = dados.get(getLayoutPosition());

                        Intent it = new Intent(context, DividasAReceber.class);
                        it.putExtra("ITEM_DIV_REC", receber);
                        ((AppCompatActivity) context).startActivityForResult(it,0);

                    }
                }
            });

        }
    }
}

