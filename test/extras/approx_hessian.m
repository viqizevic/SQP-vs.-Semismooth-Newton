function hessf = approx_hessian(f,x,epsi)
    n = length(x);
    hessf = zeros(n,n);
    for k=1:n
        ek = zeros(n,1);
        ek(k) = 1;
        for j=1:k
            ej = zeros(n,1);
            ej(j) = 1;
            hessf(k,j) = ( feval(f,x+epsi*ek+epsi*ej) - feval(f,x+epsi*ek) - feval(f,x+epsi*ej) + feval(f,x) ) / (epsi^2);
            if k == j
                hessf(k,k) = hessf(k,k)/2;
            end
        end
    end
    hessf = hessf + hessf';
end