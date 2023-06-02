package pl.inpost.recruitmenttask.ui.common

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class VerticalSpacingDecorator(
    @DimenRes private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = view.context.resources.getDimensionPixelOffset(spacing)
    }

}
