function H = opt_ctrl_prob_func_H (v, T, N)
	tao = T/N;
	H = zeros(2*N);
	H(2*N+1:2*N+2,2*N+1:2*N+2) = eye(2);
	H(2*N+3:3*N+2,2*N+3:3*N+2) = (tao*v)*eye(N);
end