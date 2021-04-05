package com.yasincidem.ciceksepeti

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yasincidem.ciceksepeti.core.Constants.SWIPE_UP_ANIM_ASSET_NAME
import com.yasincidem.ciceksepeti.core.util.ResultOf
import com.yasincidem.ciceksepeti.databinding.ActivityMainBinding
import com.yasincidem.ciceksepeti.datasource.model.Values
import com.yasincidem.ciceksepeti.ui.product.ProductListAdapter
import com.yasincidem.ciceksepeti.ui.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ProductViewModel>()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var productListAdapter: ProductListAdapter? = null
    private lateinit var bottomSheet: BottomSheetBehavior<ConstraintLayout>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomSheet = BottomSheetBehavior.from(binding.bottomSheet)

        bottomSheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED)
                    binding.bottomNav.translationY = binding.bottomSheet.height.toFloat()
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.bottomNav.translationY = bottomSheet.height.toFloat() * slideOffset
            }
        })

        binding.bottomSheet.setOnClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            binding.bottomNav.translationY = it.height.toFloat()
        }

        viewModel.isLoading().observe(this) {
            if (it == true) {
                binding.loadingOverlay.visibility = View.VISIBLE
            } else {
                lifecycleScope.launch {
                    delay(700)
                    binding.loadingOverlay.visibility = View.GONE
                }
            }
        }

        viewModel.filteredProductList.observe(this) {
            when (it) {
                is ResultOf.Success -> {
                    productListAdapter?.submitList(it.data.result?.data?.products)
                    binding.bottomSheet.visibility = View.VISIBLE
                }
            }
        }

        binding.composeView.setContent {
            MaterialTheme {
                Surface {
                    FilterList(viewModel)
                }
            }
        }

        binding.productList.apply {
            layoutManager = LinearLayoutManager(context)
            productListAdapter = ProductListAdapter()
            adapter = productListAdapter
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

@Composable
fun FilterList(viewModel: ProductViewModel) {

    val filterState by viewModel.filteredProductList.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    when (filterState) {
        is ResultOf.Success -> {

            val data =
                (filterState as? ResultOf.Success)?.data?.result?.data?.mainFilter?.dynamicFilter?.sortedBy {
                    it.sequence
                }
            val numberOfFilteredProducts =
                (filterState as? ResultOf.Success)?.data?.result?.data?.productCount


            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    content = {
                        val animationSpec = remember { LottieAnimationSpec.Asset(SWIPE_UP_ANIM_ASSET_NAME) }
                        val animationState =
                            rememberLottieAnimationState(autoPlay = true, repeatCount = 2)

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(all = 8.dp)
                        ) {
                            Column {
                                Text(
                                    style = TextStyle(fontSize = 14.sp),
                                    text = buildAnnotatedString {
                                        append(stringResource(R.string.applied_filter_count))
                                        withStyle(style = SpanStyle(color = colorResource(R.color.orange_main), fontWeight = FontWeight.Bold)) {
                                            append(viewModel.getNumberOfFilters().toString())
                                        }
                                    }
                                )
                                Text(
                                    style = TextStyle(fontSize = 14.sp),
                                    text = buildAnnotatedString {
                                        append(stringResource(R.string.product_count))
                                        withStyle(style = SpanStyle(color = colorResource(R.color.orange_main), fontWeight = FontWeight.Bold)) {
                                            append(numberOfFilteredProducts.toString())
                                        }
                                    }
                                )
                            }

                            Crossfade(
                                targetState = animationState.isPlaying || viewModel.getNumberOfFilters() == 0,
                            ) {
                                if (it) {
                                    LottieAnimation(
                                        spec = animationSpec,
                                        animationState = animationState
                                    )
                                } else {
                                    OutlinedButton(
                                        onClick = {
                                            viewModel.clearFilters()
                                        },
                                    ) {
                                        Text(
                                            stringResource(R.string.clear_filter),
                                            style = TextStyle(
                                                Color.Red
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                )

                Divider(color = colorResource(R.color.lightest_gray), thickness = 1.dp)

                data?.forEach { dynamicFilter ->
                    val scrollState = rememberLazyListState()

                    val numOfFilterAppliedEachCategory = dynamicFilter.values?.filter {
                        it.selected == true
                    }?.size

                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        text = buildAnnotatedString {
                            append("${dynamicFilter.name.toString()}:  ")
                            withStyle(style = SpanStyle(color = colorResource(R.color.orange_main))) {
                                append(numOfFilterAppliedEachCategory.toString())
                            }
                        },
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    LazyRow(
                        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                        state = scrollState,
                    ) {
                        items(
                            items = dynamicFilter.values?.sortedByDescending {
                                it.selected
                            } ?: listOf(),
                            itemContent = { filter ->
                                FilterChip(
                                    category = filter.name.toString(),
                                    isSelected = filter.selected == true,
                                    filterValue = filter,
                                    onSelectedCategoryChanged = {
                                        viewModel.updateFilteredValues(it).apply {
                                            coroutineScope.launch {
                                                scrollState.animateScrollToItem(0)
                                            }
                                        }
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterChip(
    category: String,
    isSelected: Boolean,
    filterValue: Values,
    onSelectedCategoryChanged: (Values) -> Unit,
) {
    Card(
        modifier = Modifier.padding(horizontal = 6.dp, vertical = 12.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = 0.dp,
        backgroundColor = if (isSelected) colorResource(R.color.orange_main) else colorResource(R.color.lightest_gray)
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectedCategoryChanged(filterValue)
                }
            )
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.body2,
                color = if (isSelected) Color.White else Color.Black,
                modifier = Modifier.padding(all = 12.dp)
            )
        }
    }
}
