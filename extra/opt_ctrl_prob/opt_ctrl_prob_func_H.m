function H = opt_ctrl_prob_func_H (rho_u, T, N)
	tao = T/N;
	H = zeros(2*N);
	for k=1:N
		H(2*k-1,2*k-1) = tao*rho_u;
	end
end