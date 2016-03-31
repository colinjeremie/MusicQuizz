package com.github.colinjeremie.willyoufindit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deezer.sdk.model.Album;
import com.deezer.sdk.model.Artist;
import com.deezer.sdk.model.Track;
import com.github.colinjeremie.willyoufindit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * * WillYouFindIt
 * Created by jerem_000 on 3/10/2016.
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int SECTION_TYPE = 0;
    public static final int TRACK_TYPE = 1;
    public static final int ALBUM_TYPE = 2;
    public static final int ARTIST_TYPE = 3;

    private OnAlbumClickListener mAlbumClickListener;
    private OnTrackClickListener mTrackClickListener;
    private OnArtistClickListener mArtistListener;

    private List<Track> mTracks;
    private List<Album> mAlbums;
    private List<Artist> mArtists;

    private String[] mHeadersTitle;

    public SearchAdapter(){
        mHeadersTitle = new String[3];
        mTracks = new ArrayList<>();
        mAlbums = new ArrayList<>();
        mArtists = new ArrayList<>();
    }

    public void clear(){
        mTracks.clear();
        mAlbums.clear();
        mArtists.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SECTION_TYPE){
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_section_item, parent, false);
            return new SectionViewHolder(layout);
        } else if (viewType == TRACK_TYPE){
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_track_item, parent, false);
            return new TrackViewHolder(layout);
        } else if (viewType == ALBUM_TYPE){
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_album_item, parent, false);
            return new AlbumViewHolder(layout);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_artist_item, parent, false);
        return new ArtistViewHolder(layout);
    }

    @Override
    public int getItemViewType(int position) {
        int trackSize = mTracks.size(), albumSize = mAlbums.size();

        if (position >= 0 && position <= trackSize){
            return position == 0 ? SECTION_TYPE : TRACK_TYPE;
        }
        position--;
        position -= trackSize;
        if (position >= 0 && position <= albumSize){
            return position == 0 ? SECTION_TYPE : ALBUM_TYPE;
        }
        position--;
        position -= albumSize;
        return position == 0 ? SECTION_TYPE : ARTIST_TYPE;
    }

    // bind the data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == SECTION_TYPE){
            bindSectionViewHolder((SectionViewHolder)holder, position);
        } else if (type == TRACK_TYPE){
            bindTrackViewHolder((TrackViewHolder) holder, position);
        } else if (type == ALBUM_TYPE){
            bindAlbumViewHolder((AlbumViewHolder)holder, position);
        } else if (type == ARTIST_TYPE) {
            bindArtistViewHolder((ArtistViewHolder) holder, position);
        }
    }

    private Artist getArtistFromAdapterPosition(int position){
        position -= 3;
        position -= mTracks.size() + mAlbums.size();
        return position >= 0 ? mArtists.get(position) : null;
    }

    private void bindArtistViewHolder(ArtistViewHolder holder, int position) {
        Artist model = getArtistFromAdapterPosition(position);

        Glide
                .with(holder.itemView.getContext())
                .load(model.getPictureUrl())
                .into(holder.mArtistPicture);
        holder.mName.setText(model.getName());
    }

    private Album getAlbumFromAdapterPosition(int position){
        position -= 2;
        position -= mTracks.size();
        return position >= 0 ? mAlbums.get(position) : null;
    }

    private void bindAlbumViewHolder(AlbumViewHolder holder, int position) {
        Album model = getAlbumFromAdapterPosition(position);

        Glide
                .with(holder.itemView.getContext())
                .load(model.getCoverUrl())
                .into(holder.mAlbumPicture);
        holder.mArtistName.setText(model.getArtist().getName());
        holder.mAlbumName.setText(model.getTitle());
    }

    private Track getTrackFromAdapterPosition(int position){
        return position >= 0 ? mTracks.get(position - 1) : null;
    }

    private void bindTrackViewHolder(TrackViewHolder holder, int position) {
        Track model = getTrackFromAdapterPosition(position);

        Glide
                .with(holder.itemView.getContext())
                .load(model.getArtist().getPictureUrl())
                .into(holder.mTrackImage);

        holder.mTrackTitle.setText(model.getTitle());
        holder.mArtistName.setText(model.getArtist().getName());
    }

    private void bindSectionViewHolder(SectionViewHolder holder, int position) {
        holder.mTitle.setText(mHeadersTitle[position % 3]);
    }

    @Override
    public int getItemCount() {
        int count = mTracks.size() + mAlbums.size() + mArtists.size();
        if (mTracks.size() > 0){
            count++;
        } if (mAlbums.size() > 0){
            count++;
        } if (mArtists.size() > 0){
            count++;
        }
        return count;
    }

    // setters
    public void setTracks(List<Track> tracks) {
        this.mTracks = tracks;
    }

    public void setAlbums(List<Album> albums) {
        this.mAlbums = albums;
    }

    public void setArtists(List<Artist> artists) {
        this.mArtists = artists;
    }

    public void setHeadersSection(String[] pHeaderstitle) {
        mHeadersTitle = pHeaderstitle;
    }


    // View holders
    private class TrackViewHolder extends RecyclerView.ViewHolder {
        public final TextView mArtistName;
        public final TextView mTrackTitle;
        public final ImageView mTrackImage;

        public TrackViewHolder(View itemView) {
            super(itemView);

            mArtistName = (TextView) itemView.findViewById(R.id.track_artist_name);
            mTrackTitle = (TextView) itemView.findViewById(R.id.track_artist_track);
            mTrackImage = (ImageView) itemView.findViewById(R.id.track_preview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTrackClickListener != null){
                        mTrackClickListener.onTrackClick(getTrackFromAdapterPosition(getAdapterPosition()));
                    }
                }
            });
        }
    }

    private class AlbumViewHolder extends RecyclerView.ViewHolder {
        public final TextView mAlbumName;
        public final TextView mArtistName;
        public final ImageView mAlbumPicture;

        public AlbumViewHolder(View itemView) {
            super(itemView);

            mAlbumName = (TextView) itemView.findViewById(R.id.album_album_name);
            mArtistName = (TextView) itemView.findViewById(R.id.album_artist_name);
            mAlbumPicture = (ImageView) itemView.findViewById(R.id.album_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAlbumClickListener != null){
                        mAlbumClickListener.onAlbumClick(getAlbumFromAdapterPosition(getAdapterPosition()));
                    }
                }
            });
        }
    }

    private class ArtistViewHolder extends RecyclerView.ViewHolder {
        public final TextView mName;
        public final ImageView mArtistPicture;

        public ArtistViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.artist_item_artist_name);
            mArtistPicture = (ImageView) itemView.findViewById(R.id.artist_picture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mArtistListener != null){
                        mArtistListener.onArtistClick(getArtistFromAdapterPosition(getAdapterPosition()));
                    }
                }
            });
        }
    }

    private class SectionViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitle;

        public SectionViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.section_title);
        }
    }

    public void setAlbumClickListener(OnAlbumClickListener pAlbumclicklistener) {
        mAlbumClickListener = pAlbumclicklistener;
    }

    public void setTrackClickListener(OnTrackClickListener pTrackclicklistener) {
        mTrackClickListener = pTrackclicklistener;
    }

    public void setArtistsListener(OnArtistClickListener pArtistslistener) {
        mArtistListener = pArtistslistener;
    }


    public interface OnTrackClickListener{
        void onTrackClick(Track pTrack);
    }

    public interface OnArtistClickListener{
        void onArtistClick(Artist pArtist);
    }

    public interface OnAlbumClickListener{
        void onAlbumClick(Album pAlbum);
    }
}
