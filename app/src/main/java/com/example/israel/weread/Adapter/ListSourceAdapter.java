package com.example.israel.weread.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.israel.weread.Common.Common;
import com.example.israel.weread.Interface.IconBetterIdeaService;
import com.example.israel.weread.Interface.ItemClickListener;
import com.example.israel.weread.ListNews;
import com.example.israel.weread.Model.IconBetterIdea;
import com.example.israel.weread.Model.WebSite;
import com.example.israel.weread.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ListSourceViewHolder extends RecyclerView.ViewHolder implements OnClickListener
{
    ItemClickListener itemClickListener;

    TextView source_title;
    CircleImageView source_image;

    public ListSourceViewHolder(@NonNull View itemView) {
        super(itemView);

        source_image = (CircleImageView) itemView.findViewById(R.id.source_image);
        source_title = (TextView) itemView.findViewById(R.id.source_name);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}


public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder>{
    private Context context;
    private WebSite webSite;
    private IconBetterIdeaService mService;

    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;

        mService = Common.getIconService();
    }

    @NonNull
    @Override
    public ListSourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_layout, parent, false);
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListSourceViewHolder holder, int position) {


        holder.source_title.setText(webSite.getSources().get(position).getName());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, ListNews.class);
                intent.putExtra("source", webSite.getSources().get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }
}
