function H = hess_mccormick_func(x)
	H = approx_hessian('mccormick_func',x,0.001);
end