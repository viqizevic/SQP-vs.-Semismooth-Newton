function H = hess_paviani_func_1(x)
	H = approx_hessian('paviani_func_1',x,0.001);
end