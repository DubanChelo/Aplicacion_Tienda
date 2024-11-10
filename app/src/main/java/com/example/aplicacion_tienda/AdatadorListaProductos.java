package com.example.aplicacion_tienda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdatadorListaProductos extends RecyclerView.Adapter<AdatadorListaProductos.MyViewHolder>{

    private Context context;
    //Se usa par apoder refrescar la aplicacion al hacer una actualización
    Activity activity;
    private ArrayList productos_id, productos_nombre, productos_precio, productos_cantidad;

    AdatadorListaProductos(Activity activity,Context context, ArrayList productos_id, ArrayList productos_nombre, ArrayList productos_precio, ArrayList productos_cantidad) {
        this.activity = activity;
        this.context = context;
        this.productos_id = productos_id;
        this.productos_nombre = productos_nombre;
        this.productos_precio = productos_precio;
        this.productos_cantidad = productos_cantidad;
    }

    @NonNull
    @Override

    //Se usa para crear el inflater para nuestro layaout producto
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.producto, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.Producto_id_txt.setText(String.valueOf(productos_id.get(position)));
        holder.Producto_nombre_txt.setText(String.valueOf(productos_nombre.get(position)));
        holder.Producto_precio_txt.setText(String.valueOf(productos_precio.get(position)));
        holder.Producto_cantidad_txt.setText(String.valueOf(productos_cantidad.get(position)));

        holder.ProductoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActualizarProductos.class);
                intent.putExtra("id", String.valueOf(productos_id.get(position)));
                intent.putExtra("nombre", String.valueOf(productos_nombre.get(position)));
                intent.putExtra("precio", String.valueOf(productos_precio.get(position)));
                intent.putExtra("cantidad", String.valueOf(productos_cantidad.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productos_id.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Producto_id_txt, Producto_nombre_txt, Producto_precio_txt, Producto_cantidad_txt;
        LinearLayout ProductoMain;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Producto_id_txt = itemView.findViewById(R.id.Producto_id_txt);
            Producto_nombre_txt = itemView.findViewById(R.id.Producto_nombre_txt);
            Producto_precio_txt = itemView.findViewById(R.id.Producto_precio_txt);
            Producto_cantidad_txt = itemView.findViewById(R.id.Producto_cantidad_txt);
            ProductoMain = itemView.findViewById(R.id.ProductoMain);
        }
    }
}
