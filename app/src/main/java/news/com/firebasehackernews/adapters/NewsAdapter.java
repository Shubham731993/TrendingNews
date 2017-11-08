package news.com.firebasehackernews.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import news.com.firebasehackernews.R;
import news.com.firebasehackernews.listeners.NewsListener;
import news.com.firebasehackernews.model.NewsModel;
import news.com.firebasehackernews.views.HNTextView;

/**
 * Created by shubham.srivastava on 07/11/17.
 */

public class NewsAdapter extends BaseAdapter<NewsAdapter.ViewHolder> {

  private NewsListener newsListener;
  private Context context;

  /**
   * Constructor
   * @param cursor for data
   * @param newsListener to capture on click items
   * @param context Activity
   */

  public NewsAdapter(Cursor cursor, NewsListener newsListener, Context context) {
    super(cursor);
    this.newsListener = newsListener;
    this.context=context;
  }

  /**
   * Bind Data
   * @param holder View holder.
   * @param cursor The cursor from which to get the data. The cursor is already
   */

  @Override
  public void onBindViewHolderCursor(ViewHolder holder, Cursor cursor) {
    final NewsModel news = NewsModel.from(cursor);
    setTitleAndComment(holder, news);
    bindCardClickListener(holder, news);
    bindHeader(holder, news);
    setFooter(holder,news);
  }

  /**
   * Set Footer Url
   * @param holder
   * @param newsModel
   */


  private void setFooter(ViewHolder holder, NewsModel newsModel) {
    holder.url.setText(newsModel.getUrl());
    holder.url.setTextColor(newsModel.isRead() ?
        holder.title.getResources().getColor(R.color.grey) :
        holder.title.getResources().getColor(R.color.black));
  }

  /**
   * Specify layout for recycler view
   * @param parent
   * @param viewType
   * @return
   */

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
    return new ViewHolder(v);
  }

  /**
   * Holder items for recycler view
   */

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public final HNTextView title;
    public final HNTextView url;
    public final HNTextView timeAgo;
    public final HNTextView score;
    public final HNTextView comment;
    public final RelativeLayout relativeLayout;
    private ImageView fireIcon;

    public ViewHolder(View view) {
      super(view);
      title = view.findViewById(R.id.title);
      timeAgo = view.findViewById(R.id.time);
      url = view.findViewById(R.id.url);
      score = view.findViewById(R.id.score);
      comment = view.findViewById(R.id.comment_value);
      relativeLayout = view.findViewById(R.id.parent_layout);
      fireIcon = view.findViewById(R.id.fire_score);
    }
  }

  /**
   * Bind item listener
   * @param holder
   * @param newsModel
   */


  private void bindCardClickListener(ViewHolder holder, final NewsModel newsModel) {
    holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        newsListener.onViewItemClicked(newsModel);
      }
    });
  }

  /**
   * Bind Header Data
   * @param holder
   * @param newsModel
   */


  private void bindHeader(final ViewHolder holder, final NewsModel newsModel) {
    holder.score.setText(holder.score.getResources().getString(R.string.news_score, newsModel.getScore()));
    holder.score.setTextColor(newsModel.isRead() ?
        holder.title.getResources().getColor(R.color.grey) :
        holder.title.getResources().getColor(R.color.black));
    holder.timeAgo.setTextColor(newsModel.isRead() ?
        holder.title.getResources().getColor(R.color.grey) :
        holder.title.getResources().getColor(R.color.black));
  }

  /**
   * Bind Title and Comment
   * @param holder
   * @param newsModel
   */

  private void setTitleAndComment(ViewHolder holder, NewsModel newsModel) {
    holder.title.setTextColor(newsModel.isRead() ?
        holder.title.getResources().getColor(R.color.grey) :
        holder.title.getResources().getColor(R.color.black));
    holder.title.setText(newsModel.getTitle());
    holder.comment.setText(String.valueOf(newsModel.getComments()));
    holder.comment.setTextColor(newsModel.isRead() ?
        holder.title.getResources().getColor(R.color.grey) :
        holder.title.getResources().getColor(R.color.black));

    holder.fireIcon.setImageDrawable(newsModel.isRead() ? ContextCompat.getDrawable(context,R.drawable.fire_disabled): ContextCompat.getDrawable(context,R.drawable.fire_score));

  }


}
