function x0 = opt_ctrl_prob_constr_x0 (z0, T, N)
	tao = T/N;
	x0 = zeros(2*N,1);
	z = 5;
	x0(1) = func_r(0)-(z-z0)/tao;
	x0(2) = z;
	for k=2:N
		x0(2*k-1) = func_r(tao*(k-1));
		x0(2*k) = z;
	end
end

function obj = func_r(x)
    obj = 10 + 5*sin(x);
end
