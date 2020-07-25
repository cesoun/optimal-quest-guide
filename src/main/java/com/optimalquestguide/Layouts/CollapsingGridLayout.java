package com.optimalquestguide.Layouts;

import java.awt.*;
import java.util.function.Function;

/**
 * Grid Layout implementation with support for dynamic sizing based on cell visibility.
 *
 * Functions the same as DynamicGridLayout but collapses the space between cells when they have
 * their visibility set to false. By default the space remains there and they would
 * need to be from the grid.
 */
public class CollapsingGridLayout extends GridLayout
{
    public CollapsingGridLayout()
    {
        this(1, 0, 0, 0);
    }

    public CollapsingGridLayout(int rows, int cols)
    {
        this(rows, cols, 0, 0);
    }

    public CollapsingGridLayout(int rows, int cols, int hgap, int vgap)
    {
        super(rows, cols, hgap, vgap);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent)
    {
        synchronized (parent.getTreeLock())
        {
            return calculateSize(parent, Component::getPreferredSize);
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent)
    {
        synchronized (parent.getTreeLock())
        {
            return calculateSize(parent, Component::getMinimumSize);
        }
    }

    @Override
    public void layoutContainer(Container parent)
    {
        synchronized (parent.getTreeLock())
        {
            final Insets insets = parent.getInsets();
            final int ncomponents = getVisibleComponents(parent);
            int nrows = getRows();
            int ncols = getColumns();

            if (ncomponents == 0)
            {
                return;
            }

            if (nrows > 0)
            {
                ncols = (ncomponents + nrows - 1) / nrows;
            }
            else
            {
                nrows = (ncomponents + ncols - 1) / ncols;
            }

            final int hgap = getHgap();
            final int vgap = getVgap();

            // scaling factors
            final Dimension pd = preferredLayoutSize(parent);
            final Insets parentInsets = parent.getInsets();
            int wborder = parentInsets.left + parentInsets.right;
            int hborder = parentInsets.top + parentInsets.bottom;
            final double sw = (1.0 * parent.getWidth() - wborder) / (pd.width - wborder);
            final double sh = (1.0 * parent.getHeight() - hborder) / (pd.height - hborder);

            final int[] w = new int[ncols];
            final int[] h = new int[nrows];

            // calculate dimensions for all visible components + apply scaling
            for (int i = 0; i < parent.getComponentCount(); i++)
            {
                final int r = i / ncols;
                final int c = i % ncols;
                final Component comp = parent.getComponent(i);

                if (!comp.isVisible()) continue;

                final Dimension d = comp.getPreferredSize();
                d.width = (int) (sw * d.width);
                d.height = (int) (sh * d.height);

                if (w[c] < d.width)
                {
                    w[c] = d.width;
                }

                if (h[r] < d.height)
                {
                    h[r] = d.height;
                }
            }

            // Apply new bounds to all child components
            for (int c = 0, x = insets.left; c < ncols; c++)
            {
                for (int r = 0, y = insets.top; r < nrows; r++)
                {
                    int i = r * ncols + c;

                    if (i < parent.getComponentCount())
                    {
                        if (!parent.getComponent(i).isVisible()) continue;

                        parent.getComponent(i).setBounds(x, y, w[c], h[r]);
                    }

                    y += h[r] + vgap;
                }

                x += w[c] + hgap;
            }
        }
    }

    /**
     * Calculate outer size of the layout based on it's children and sizer
     * @param parent parent component
     * @param sizer functioning returning dimension of the child component
     * @return outer size
     */
    private Dimension calculateSize(final Container parent, final Function<Component, Dimension> sizer)
    {
        final int ncomponents = getVisibleComponents(parent);
        int nrows = getRows();
        int ncols = getColumns();

        if (nrows > 0)
        {
            ncols = (ncomponents + nrows - 1) / nrows;
        }
        else
        {
            nrows = (ncomponents + ncols - 1) / ncols;
        }

        final int[] w = new int[ncols];
        final int[] h = new int[nrows];

        // Calculate dimensions for all visible components
        for (int i = 0; i < parent.getComponentCount(); i++)
        {
            final int r = i / ncols;
            final int c = i % ncols;
            final Component comp = parent.getComponent(i);

            // Ignore not visible components.
            if (!comp.isVisible()) continue;

            final Dimension d = sizer.apply(comp);

            if (w[c] < d.width)
            {
                w[c] = d.width;
            }

            if (h[r] < d.height)
            {
                h[r] = d.height;
            }
        }

        // Calculate total width and height of the layout
        int nw = 0;

        for (int j = 0; j < ncols; j++)
        {
            nw += w[j];
        }

        int nh = 0;

        for (int i = 0; i < nrows; i++)
        {
            nh += h[i];
        }

        final Insets insets = parent.getInsets();

        // Apply insets and horizontal and vertical gap to layout
        return new Dimension(
                insets.left + insets.right + nw + (ncols - 1) * getHgap(),
                insets.top + insets.bottom + nh + (nrows - 1) * getVgap());
    }

    /**
     * Get the count of visible child components
     * @param parent parent component
     * @return visible count
     */
    private int getVisibleComponents(Container parent) {
        int visible = 0;

        for (Component c : parent.getComponents())
            if (c.isVisible())
                visible++;

        return visible;
    }

    /**
     * Get the count of hidden child components
     * @param parent parent component
     * @return hidden count
     */
    private int getHiddenComponents(Container parent) {
        int hidden = 0;

        for (Component c : parent.getComponents())
            if (c.isVisible())
                hidden++;

        return hidden;
    }
}