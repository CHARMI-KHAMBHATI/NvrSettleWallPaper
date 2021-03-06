package com.kc.unsplashdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.kc.unsplash.models.Photo;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.ViewHolder> {

    private final List<Photo> photoList;
    private Context mContext;

    public PhotoRecyclerAdapter(List<Photo> photos, Context context) {
        photoList = photos;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
         Photo photo = photoList.get(position);

        Picasso.with(mContext).load(photo.getUrls().getRegular()).into(holder.imageView);




    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public  ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);



            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(mContext, "an image clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, EditImage.class);

                    imageView.buildDrawingCache();
                    Bitmap bitmap = imageView.getDrawingCache();



                    //Bundle extras = new Bundle();
                    //extras.putParcelable("imagebitmap", bitmap);
                   // intent.putExtras(extras);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bytes = stream.toByteArray();
                    intent.putExtra("bitmapbytes",bytes);
                    mContext.startActivity(intent);


                }
            });

        }



    }
}
