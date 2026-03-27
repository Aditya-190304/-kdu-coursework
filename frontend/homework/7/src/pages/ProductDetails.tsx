import React,{useEffect,useState} from "react";
import { useParams,useNavigate} from "react-router-dom";
import type { Product } from "../types/product.types";
import { API_URL } from "../constants/product.constants";
import styling from './ProductDetails.module.scss';

const ProdDetails: React.FC = () => {
    const {id}=useParams<{id:string}>();
    const navigate=useNavigate();
    const [product,setProduct]=useState<Product|null>(null);
    const [loading,setLoading]=useState<boolean>(true);
    const [error,setError]=useState<string|null>(null);

    useEffect(()=>{
        if(!id||isNaN(Number(id))){
            setError("invalid product ID");
            setLoading(false);
            return;
        }
        const fetchdetails=async()=>{
            try{
                setError(null);
                const response=await fetch(`${API_URL}/${id}`);
                if(!response.ok) throw new Error();
                const data:Product=await response.json();
                setProduct(data);
            }catch(err){
                setError("Failed to fetch product details");
            }finally{
                setLoading(false);
            }
        };
        fetchdetails();
    },[id]);

        if(loading)return <div className={styling.loading}>Loading...</div>;
        if(error)return <div className={styling.error}>{error}</div>;
        if(!product)return <div className={styling.error}>Product not found</div>;

        return(
            <div className={styling.details}>
                <button onClick={()=>navigate(-1)} className={styling.backbtn}>Back</button>
                <div className={styling.layout}>
                    <div className={styling.images}>
                        <img src={product.thumbnail} alt={product.title} className={styling.mainImage}/>
                        <div className={styling.gallery}>
                            {product.images.map((img,i)=><img key={i} src={img} alt="detail"/>)}
                        </div>
                    </div>
                    <div className={styling.content}>
                        <p className={styling.brand}>{product.brand}</p>
                        <h1>{product.title}</h1>
                        <p className={styling.description}>{product.description}</p>
                        <p className={styling.price}>${product.price}</p>
                        <p>Rating: {product.rating} </p>
                        <p>Stock: {product.stock}</p>
                    </div>
                </div>
                    </div>
        );
    };
    export default ProdDetails;