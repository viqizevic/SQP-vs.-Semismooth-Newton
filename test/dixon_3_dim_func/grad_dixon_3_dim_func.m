function g = grad_dixon_3_dim_func(x)
	g = approx_gradient('dixon_3_dim_func',x,0.001);
end