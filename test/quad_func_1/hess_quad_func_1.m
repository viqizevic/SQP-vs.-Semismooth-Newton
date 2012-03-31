function H = hess_quad_func_1(x)
	xd = [4; 7];
	H = approx_hessian('quad_func_1',x,0.001);
end