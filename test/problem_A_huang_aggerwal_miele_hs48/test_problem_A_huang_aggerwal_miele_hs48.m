function [x_ssn,it_ssn,t_ssn,x_sqp,it_sqp,t_sqp] = test_problem_A_huang_aggerwal_miele_hs48(show)
    u = [];
    v = [];
    x0 = [3; 5; -3; 2; -2];
    tol = 0.00001;
    itmax = 100;
    A = [1 1 1 1 1; 0 0 1 -2 -2];
    b = [5; -3];
    G = [];
    r = [];
    G = [ G; -eye(length(u)); eye(length(v)) ];
    r = [ r; -u; v ];
    tic;
    %[x_ssn,fval_ssn,it_ssn] = active_set_strategy('quad_func_4','grad_quad_func_4','hess_quad_func_4',A,b,G,r,x0,itmax,tol);
    [x_ssn,fval_ssn,it_ssn] = semismooth_newton('quad_func_4','grad_quad_func_4','hess_quad_func_4',A,b,G,r,x0,itmax,tol);
    t_ssn = toc;
    x1 = sprintf('%.3f ',x_ssn);
    f1 = sprintf('f(x_ssn) = %.3f',fval_ssn);
    t1 = sprintf('solved in %.2f ms.',t_ssn*1000);
    str1 = ['x_ssn = [ ', x1, '], ', f1, ', it = ', num2str(it_ssn), ', ', t1];
    tic;
    [x_sqp,fval_sqp,it_sqp] = seq_quad_prog('quad_func_4','grad_quad_func_4','hess_quad_func_4',A,b,G,r,x0,itmax,tol);
    t_sqp = toc;
    x2 = sprintf('%.3f ',x_sqp);
    f2 = sprintf('f(x_sqp) = %.3f',fval_sqp);
    t2 = sprintf('solved in %.2f ms.',t_sqp*1000);
    str2 = ['x_sqp = [ ', x2, '], ', f2, ', it = ', num2str(it_sqp), ', ', t2];
    if ( nargin == 0 )
        show = 1;
    end
    if ( show == 1 )
        u = sprintf('%.3f ',u);
        v = sprintf('%.3f ',v);
        x0 = sprintf('%.3f ',x0);
        str0 = ['u = [ ', u, '], v = [ ', v, '], x0 = [ ', x0, ']'];
        disp(str0);
        disp(str1);
        disp(str2);
    end
end
