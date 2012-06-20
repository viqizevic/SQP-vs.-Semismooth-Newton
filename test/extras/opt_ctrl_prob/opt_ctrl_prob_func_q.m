function q = opt_ctrl_prob_func_q (rho_z,s,T,N)
	tao = T/N;
	q = zeros(2*N,1);
	for k=1:N-1
		q(2*k) = tao*rho_z;
	end
	q(2*N) = tao*rho_z - s;
end