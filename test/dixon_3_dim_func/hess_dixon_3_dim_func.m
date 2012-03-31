function H = hess_dixon_3_dim_func(x)
	H = approx_hessian('dixon_3_dim_func',x,0.001);
end