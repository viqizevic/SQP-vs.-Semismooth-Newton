function b = opt_ctrl_prob_constr_b (z0, T, N)
	tao = T/N;
	b = zeros(N,1);
	b(1) = z0-tao*func_r(0);
	for k=2:N
		b(k) = -tao*func_r(tao*(k-1));
	end
end

function obj = func_r(x)
    obj = 10 + 5*sin(x);
end
