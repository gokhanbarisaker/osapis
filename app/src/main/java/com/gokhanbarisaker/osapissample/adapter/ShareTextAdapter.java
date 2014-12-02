package com.gokhanbarisaker.osapissample.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gokhanbarisaker.osapissample.R;
import com.gokhanbarisaker.osapissample.utilities.RandomColorGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gokhanbarisaker on 11/30/14.
 */
public class ShareTextAdapter extends PagerAdapter
{
    private static final List<String> textList = new ArrayList<>(4);

    static {
        textList.add(null);
        textList.add("Hello");
        textList.add("Signing up for a Microsoft account to test the tech preview… “Your password can't be longer than 16 characters.”");
        textList.add("High Life brunch roof party health goth PBR, slow-carb bitters lo-fi organic Thundercats Pinterest. VHS shabby chic kale chips, flexitarian Portland blog keytar kogi literally tattooed Marfa typewriter. Blog chambray cray Helvetica, wolf tousled Pinterest lomo lumbersexual swag literally American Apparel. Tonx occupy next level Pinterest VHS Odd Future cred. Truffaut ugh viral literally, crucifix fixie retro before they sold out cold-pressed twee VHS whatever meggings photo booth. Godard scenester jean shorts leggings, pork belly keffiyeh listicle cronut skateboard Carles. Tumblr semiotics tofu, pop-up health goth American Apparel gastropub Godard umami tousled.");
    }

    @Override
    public int getCount()
    {
        return textList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View rootView = inflater.inflate(R.layout.item_share_text, container, false);
        rootView.setBackgroundColor(RandomColorGenerator.generate());

        TextView textView = (TextView) rootView.findViewById(R.id.share_text);
        textView.setText(textList.get(position));

        return rootView;
    }
}
