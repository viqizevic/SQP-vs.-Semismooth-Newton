function H = hess_holzmann_func(x)
	H = approx_hessian('holzmann_func',x,0.001);
end