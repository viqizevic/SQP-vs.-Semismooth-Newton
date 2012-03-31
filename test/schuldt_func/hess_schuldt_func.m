function H = hess_schuldt_func(x)
	H = approx_hessian('schuldt_func',x,0.001);
end