package com.example.gabor.recappt.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabor.recappt.DatabaseHelper;
import com.example.gabor.recappt.ItemClickListener;
import com.example.gabor.recappt.R;
import com.example.gabor.recappt.Recipe;
import com.example.gabor.recappt.RecipeItemActivity;

import java.util.List;



class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

    public TextView recipeName,recipeCategory,recipeTime;
    public ImageView recipePicture;
    private ItemClickListener itemClickListener;







    public SearchViewHolder(View itemView){
        super(itemView);
        recipeName = (TextView)itemView.findViewById(R.id.tv_recieName);
        recipeCategory = (TextView)itemView.findViewById(R.id.tv_recieCategory);
        recipeTime = (TextView)itemView.findViewById(R.id.tv_recieTime);
        recipePicture = (ImageView)itemView.findViewById(R.id.imageView_recipePicture);

        recipePicture.setOnClickListener(this);
        recipePicture.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){

        this.itemClickListener = itemClickListener;

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{

    private Context context;
    private List<Recipe> recipes;
    public DatabaseHelper db;
    byte[] bytekep;
    Bitmap bitmapkep;







    public SearchAdapter (Context context, List<Recipe> recipes){
        this.context = context;
        this.recipes = recipes;
        this.db = new DatabaseHelper(context);

    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_recipes,parent,false);
        return new SearchViewHolder(itemView);
    }






    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {





        Bitmap kep = db.getImage(recipes.get(position).getId());
        holder.recipeName.setText("Name: "+recipes.get(position).getName());
        holder.recipeCategory.setText("Category: "+recipes.get(position).getCategory());
        holder.recipeTime.setText("Time: "+ recipes.get(position).getTime()+" Min");
        //holder.recipePicture.setImageBitmap(bytekep);
        holder.recipePicture.setImageBitmap(kep);


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, "Long Click: " + recipes.get(position).getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), RecipeItemActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    Bundle bundle = new Bundle();
                    bundle.putString("recipeName", recipes.get(position).getName());
                    bundle.putString("recipeCategory", recipes.get(position).getCategory());
                    bundle.putString("recipeTime", recipes.get(position).getTime());
                    bundle.putString("recipeIngredients", recipes.get(position).getIngredients());
                    bundle.putString("recipeSteps", recipes.get(position).getSteps());
                    bundle.putInt("id",recipes.get(position).getId());
                    //bundle.putParcelable("recipePicture", db.getImage(recipes.get(position).getId()));

                    intent.putExtras(bundle);
                    view.getContext().startActivity(intent);
                    ((Activity)context).finish();

                }

                else
                    Toast.makeText(context,"    Click: "+recipes.get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });



    }




    @Override
    public int getItemCount() {
        return recipes.size();
    }
}

