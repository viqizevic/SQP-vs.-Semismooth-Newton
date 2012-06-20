function A = opt_ctrl_prob_constr_A (T, N)
	tao = T/N;
	A = zeros(N,2*N);
	A(1,1) = -tao;
	A(1,2) = 1;
	for k=2:N
		A(k,2*k-2) = -1;
		A(k,2*k-1) = -tao;
		A(k,2*k) = 1;
	end
end