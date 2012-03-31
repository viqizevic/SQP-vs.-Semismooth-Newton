function H = hess_exp_func(x)
	H = approx_hessian('exp_func',x,0.001);
end