function g = grad_dixon_2_dim_func(x)
	g = approx_gradient('dixon_2_dim_func',x,0.001);
end